package gvs.repository;

import gvs.dto.SweetModel;

import java.util.*;

public class SweetProductRepository {

    private Map<String, SweetModel> sweetModelMap = new HashMap<>();

    public String add(String productKey, SweetModel sweetModel) {
        if (sweetModelMap.containsKey(productKey)) {
            sweetModelMap.put(productKey, sweetModel);
            return sweetModelMap.get(productKey).toString();
        } else {
            List<SweetModel> list = new LinkedList<>();
            list.add(sweetModel);
            sweetModelMap.put(productKey, sweetModel);
            return "1";
        }
    }

    public List<SweetModel> getList() {
        List<SweetModel> sweetModels = new ArrayList<>();
        for (SweetModel sweetModel : sweetModelMap.values()) {
            sweetModels.add(sweetModel);
        }
        return sweetModels;
    }

    public SweetModel getItem(String productKey) {
        if (sweetModelMap.containsKey(productKey)) {
            return sweetModelMap.get(productKey);
        }
        return null;
    }
//
//    public boolean delete(Long userId, String id) {
//        ProductItem productItem = this.getItem(userId, id);
//        if (productItem != null) {
//            this.sweetModelMap.get(userId).remove(productItem);
//            return true;
//        }
//        return false;
//    }

}
