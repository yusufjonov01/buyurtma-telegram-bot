package gvs.repository;

import gvs.dto.OrderItem;
import gvs.enums.OrderItemType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {
    public Map<Integer, OrderItem> orderMap = new HashMap<>();

    public boolean addOrder(Integer key, OrderItem orderItem) {
        orderMap.put(key, orderItem);
        return true;
    }

    public boolean deleteOrder(Integer key) {
        OrderItem orderItem = orderMap.get(key);
        if (orderItem.getOrderItemType().equals(OrderItemType.SELECTED)) {
            orderMap.remove(key);
            return true;
        }
        return false;
    }

    public OrderItem getOrder(String key) {
        if (orderMap.containsKey(key)) {
            return orderMap.get(key);
        }
        return null;
    }

    public OrderItem getOrderByUserId(Long userId) {
        for (OrderItem order : orderMap.values()) {
            if (order.getUserId().equals(userId) && order.getOrderItemType().equals(OrderItemType.SELECTED)) {
                return order;
            }
        }
        return null;
    }

    public List<OrderItem> getOrderList() {
        return (List<OrderItem>) orderMap.values();
    }

    public OrderItem getUserOrderList(Long userId) {
        for (OrderItem order : orderMap.values()) {
            if (order.getUserId().equals(userId) &&
                    order.getOrderItemType() == OrderItemType.SELECTED) {
                return orderMap.get(order.getId());
            }
        }
        return null;
    }
}
