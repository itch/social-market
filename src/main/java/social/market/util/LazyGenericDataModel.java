package social.market.util;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
public class LazyGenericDataModel<T> extends LazyDataModel<T> {

    private List<T> datasource;

    public LazyGenericDataModel(List<T> datasource) {
        this.datasource = datasource;
    }

    @Override
    public T getRowData(String rowKey) {
        String id = null;
        for (T dataObject : datasource) {           
            try {
             id =   String.valueOf( dataObject.getClass().getMethod("getId").invoke(dataObject));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (id.equals(rowKey)){
                return dataObject;
                
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(T dataObject) {
        Object rowKey = null;
        try {
            rowKey =  dataObject.getClass().getMethod("getId").invoke(dataObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return rowKey;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<T> data = new ArrayList<T>();
        for (T dataObject : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);

                        filterProperty = "get" + Character.toUpperCase(filterProperty.charAt(0)) + filterProperty.substring(1);

                        String fieldValue = String.valueOf(dataObject.getClass().getMethod(filterProperty).invoke(dataObject));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(dataObject);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazyGenericSorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }
}