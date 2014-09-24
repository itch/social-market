package social.market.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import social.market.filter.LoginPageFilter;
import social.market.storage.model.Category;
import social.market.storage.model.Product;
import social.market.storage.model.User;
import social.market.storage.repository.CategoryRepository;
import social.market.storage.repository.ProductRepository;
import social.market.util.LazyGenericDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@Component
@Lazy
public class CpProductPositionView {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Product newProduct = new Product();
    private Product selectedProduct;
    private Product[] selectedProducts;
    private LazyGenericDataModel products;
    private List<Category> categoryList;

    @PostConstruct
    public void init(){
        System.out.println("loadProductData");
        loadProductData();
    }

    private void loadProductData() {
       List<Product> load_products = productRepository.getUserProducts(LoginPageFilter.getLoggedUser());
        products = new LazyGenericDataModel(load_products);
    }

    public LazyGenericDataModel getProducts() {
        return products;
    }


    public Product getSelectedProduct() {
        return selectedProduct;
    }


    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }


    public Product[] getSelectedProducts() {
        return selectedProducts;
    }


    public void setSelectedProducts(Product[] selectedProducts) {
        this.selectedProducts = selectedProducts;
    }


    public void doDeleteProducts() {
            System.out.println("productRepository" + productRepository);
        for (Product product : selectedProducts) {
            System.out.println("doDeleteProducts:" + product);
            productRepository.delete(product);
        }

        loadProductData();
    }

    public void doUpdateProduct() {
        System.out.println("doUpdateProduct" + selectedProduct);
        productRepository.update(selectedProduct);
    }

    public void doCreateProduct() {
        newProduct.setUser(LoginPageFilter.getLoggedUser());
        productRepository.save(newProduct);
        loadProductData();
    }

    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    public List<Category> getCategoryList() {
        if (categoryList == null) {
            categoryList = this.categoryRepository.getUserCategories(LoginPageFilter.getLoggedUser());
        }
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setProductRepository(ProductRepository productRepository) {
        System.out.println("setProductRepository");
        this.productRepository = productRepository;
    }

    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
