package social.market.view;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import social.market.filter.LoginPageFilter;
import social.market.storage.model.Category;
import social.market.storage.model.User;
import social.market.storage.repository.CategoryRepository;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.util.List;

@Component
@Lazy
public class CpCategoryView {
    Category rootCategory;
    TreeNode categoryRootNode;
    Category selectedCategory;

    @Autowired
    CategoryRepository categoryRepository;

    @PostConstruct
    public void init(){
        System.out.println("load data");
        loadData();
    }

    public TreeNode newNodeWithChildren(Category ttParent, TreeNode parent) {
        TreeNode newNode = new DefaultTreeNode(ttParent, parent);

        for (Category tt : ttParent.getChildren()) {
            newNodeWithChildren(tt, newNode);
        }
        return newNode;
    }

    private TreeNode innerNode(List<TreeNode> nodeList, Category category) {
        for (TreeNode node : nodeList) {
            if (node.getData().equals(category)) {
                return node;
            }
            if (node.getChildCount() > 0) {
                TreeNode foundNode = innerNode(node.getChildren(), category);
                if (foundNode != null) {
                    return foundNode;
                }
            }

        }
        return null;
    }

    private TreeNode findNode(Category category) {

        return innerNode(categoryRootNode.getChildren(), category);

    }

    private void loadData() {
        User loggedUser = LoginPageFilter.getLoggedUser();
        if (loggedUser != null) {
            rootCategory = categoryRepository.getUserRootCategory(loggedUser);
            categoryRootNode = newNodeWithChildren(rootCategory, null);
        }
    }


    public void doUpdateCategory() {
        categoryRepository.update(selectedCategory);
        loadData();
    }

    public void doAddSubCategory() {

        Category newSubCategory = new Category();
        newSubCategory.setParent(selectedCategory);
        newSubCategory.setUser(LoginPageFilter.getLoggedUser());
        newSubCategory.setName("New sub category");
        categoryRepository.save(newSubCategory);
        selectedCategory.getChildren().add(newSubCategory);

        TreeNode node = findNode(selectedCategory);
        if (node != null) {
            new DefaultTreeNode(newSubCategory, node);
        }
        loadData();
    }

    public void doAddRootCategory() {

        Category newRootCategory = new Category();

        newRootCategory.setParent(rootCategory);

        newRootCategory.setUser(LoginPageFilter.getLoggedUser());

        newRootCategory.setName("New root category");
        categoryRepository.save(newRootCategory);
        new DefaultTreeNode(newRootCategory, categoryRootNode);

        rootCategory.getChildren().add(newRootCategory);
        loadData();
    }

    public void doDeleteCategory() {
        TreeNode node = findNode(selectedCategory);
        if (node != null) {
            node.getParent().getChildren().remove(node);
        }
        recursiveDeleteCategory(selectedCategory);
        loadData();
    }

    private void recursiveDeleteCategory(Category category) {
        List<Category> children = category.getChildren();
        for (Category cat : children) {
            if (category.getChildren() != null) {
                recursiveDeleteCategory(cat);

            }
            categoryRepository.delete(cat);
        }
        children.clear();
        categoryRepository.delete(category);
        loadData();
    }


    public TreeNode getCategoryRootNode() {

        return categoryRootNode;
    }

    public void setCategoryRootNode(TreeNode categoryRootNode) {
        this.categoryRootNode = categoryRootNode;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }


}
