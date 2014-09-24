package social.market.util;




import social.market.storage.model.Category;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.HashMap;


@FacesConverter("social.market.util.ObjectCategoryConverter")
public class ObjectCategoryConverter implements Converter {

    private static HashMap<String, Category> map = new HashMap<String, Category>();
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Category category = map.get(value);
        return category;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Category category = (Category)value;

        map.put(category.getId().toString(), category);
        return category.getId().toString();
    }
}
