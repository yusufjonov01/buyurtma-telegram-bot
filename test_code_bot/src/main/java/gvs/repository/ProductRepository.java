package gvs.repository;

import gvs.dto.ProductItem;
import gvs.enums.OrderItemType;
import gvs.enums.ProductItemType;

import java.util.*;

public class ProductRepository {

    public Map<String, ProductItem> productMap = new HashMap<>();

    public boolean addProduct(String key, ProductItem productItem) {
        productMap.put(key, productItem);
        return true;
    }

    public boolean deleteProduct(String key) {
        if (productMap.containsKey(key)) {
            productMap.remove(key);
            return true;
        } else {
            return false;
        }
    }

    public ProductItem getProduct(String key) {
        if (productMap.containsKey(key)) {
            return productMap.get(key);
        } else {
            return null;
        }
    }

    public ProductItem getProduct(ProductItemType productItemType) {
        for (ProductItem productItem : productMap.values()) {
            if (productItem.getProductItemType().equals(productItemType)) {
                return productItem;
            }
        }
        return null;
    }

    public List<ProductItem> getProducts() {
        Collection<ProductItem> values = productMap.values();
        return new ArrayList<ProductItem>(values);
    }

    public boolean changeProductActive(String key) {
        if (productMap.containsKey(key)) {
            ProductItem productItem = productMap.get(key);
            productItem.setActive(!productItem.isActive());
            productMap.put(key, productItem);
            return true;
        }
        return false;
    }

//    public int add(Long userId, ProductItem productItem) {
//        if (productMap.containsKey(userId)) {
//            List<ProductItem> productItems = productMap.get(userId);
//            for (ProductItem product : productItems) {
//                if (product.getTitle().equals(productItem.getTitle())) {
//                    product.setCount(productItem.getCount());
//                    return 1;
//                }
//            }
//            productMap.get(userId).add(productItem);
//            return 1;
//        } else {
//            List<ProductItem> list = new LinkedList<>();
//            list.add(productItem);
//            productMap.put(userId, list);
//            return 1;
//        }
//    }
//
//    public List<ProductItem> getTodoList(Long userId) {
//        if (productMap.containsKey(userId)) {
//            return productMap.get(userId);
//        }
//        return null;
//    }
//
//
//    public ProductItem getItem(Long userId, String id) {
//        if (productMap.containsKey(userId)) {
//            List<ProductItem> list = productMap.get(userId);
//            for (ProductItem productItem : list) {
//                if (productItem.getId().equals(id)) {
//                    return productItem;
//                }
//            }
//
//        }
//        return null;
//    }
//
//    public boolean delete(Long userId, String id) {
//        ProductItem productItem = this.getItem(userId, id);
//        if (productItem != null) {
//            this.productMap.get(userId).remove(productItem);
//            return true;
//        }
//        return false;
//    }

}
