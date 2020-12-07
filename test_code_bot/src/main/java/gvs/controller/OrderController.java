package gvs.controller;

import gvs.dto.CodeMessage;
import gvs.dto.OrderItem;
import gvs.dto.ProductItem;
import gvs.dto.SweetModel;
import gvs.enums.CodeMessageType;
import gvs.enums.OrderItemType;
import gvs.repository.OrderRepository;
import gvs.repository.ProductRepository;
import gvs.util.InlineButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class OrderController {
    private ProductRepository productRepository = new ProductRepository();
    private OrderRepository orderRepository = new OrderRepository();
    private Map<Integer, OrderItem> orderItemMap = new HashMap<>();
    private GeneralController generalController = new GeneralController();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public CodeMessage handle(String text, Long chatId, Integer messageId) {
        CodeMessage codeMessage = new CodeMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (text.startsWith("/order/")) {
            String[] commandList = text.split("/");
            String command = commandList[2];
            if (commandList.length == 3) {

                switch (command) {
                    case "sweet":
//                        sendMessage.setReplyMarkup(this.getProductKeyBoard("sweet"));
                        sendMessage.setText("<b>Pechenyelar ro'yhati:</b>");
                        break;
                    case "drink":
//                        sendMessage.setReplyMarkup(this.getProductKeyBoard("drink"));
                        sendMessage.setText("<b>Ichimliklar ro'yhati:</b>");
                        break;
                    case "diaper":
//                        sendMessage.setReplyMarkup(this.getProductKeyBoard("diaper"));
                        sendMessage.setText("<b>Tagliklar ro'yhati:</b>");
                        break;
                }
                sendMessage.setParseMode("HTML");
                sendMessage.setReplyMarkup(this.getProductKeyBoard(command));
                if (productRepository.productMap != null) {
                    System.out.println(productRepository.productMap.values());
                } else {
                    System.out.println("bo'sh");
                }
                codeMessage.setSendMessage(sendMessage);
                codeMessage.setType(CodeMessageType.MESSAGE);
                return codeMessage;
            } else if (commandList.length == 4) {
                String selectedProduct = commandList[3];
                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                        InlineButtonUtil.collection(
                                InlineButtonUtil.row(
                                        InlineButtonUtil.button("1", "/product/" + command + "/" + selectedProduct + "/1"),
                                        InlineButtonUtil.button("2", "/product/" + command + "/" + selectedProduct + "/2"),
                                        InlineButtonUtil.button("3", "/product/" + command + "/" + selectedProduct + "/3"),
                                        InlineButtonUtil.button("4", "/product/" + command + "/" + selectedProduct + "/4"),
                                        InlineButtonUtil.button("5", "/product/" + command + "/" + selectedProduct + "/5")
                                ), InlineButtonUtil.row(
                                        InlineButtonUtil.button("6", "/product/" + command + "/" + selectedProduct + "/6"),
                                        InlineButtonUtil.button("7", "/product/" + command + "/" + selectedProduct + "/7"),
                                        InlineButtonUtil.button("8", "/product/" + command + "/" + selectedProduct + "/8"),
                                        InlineButtonUtil.button("9", "/product/" + command + "/" + selectedProduct + "/9"),
                                        InlineButtonUtil.button("10", "/product/" + command + "/" + selectedProduct + "/10")

                                ),
                                InlineButtonUtil.row(InlineButtonUtil.button("Menyu", "menu"))
                        )));
                ProductItem product = this.productRepository.getProduct(selectedProduct);
//                sendMessage.setText("Nomi: <b>" + product.getName() + "</b> \n<b>" +
//                        (String.valueOf(product.getWeight()).length() > 3 ?
//                                (product.getWeight() / 1000) + "</b> kg" :
//                                product.getWeight() + " gr") + " - " +
//                        product.getPrice() + " so'm\n" +
//                        "1 karobkada - " +
//
//                        (String.valueOf(product.getAllWeight()).length() > 3 ?
//                                (product.getAllWeight() / 1000) + " kg" :
//                                product.getAllWeight() + " gr") + "\n" +
//
//                        "Narxi - <b>" + (
//                        (product.getAllWeight() / product.getWeight())
//                                * product.getPrice()) + " so'm </b>");
                sendMessage.setText("Nomi: <b>" + product.getName() + "</b>\n" +
                        "Vazni: " + product.getAmount() + " " + product.getWeightType() + "\n" +
                        "Sof vazni: " + product.getAllAmount() + " " + product.getWeightType() + "\n" +
                        "Narxi: " + product.getPrice() + " so'm\n" +
                        "Umimiy narxi: " + product.getAllPrice() + " so'm\n" +
                        "Buyurtmanigiz sonini tanlang:");
            } else if (commandList.length == 5) {
                String selectedProduct = commandList[3];
                String productCount = commandList[4];
                ProductItem product = this.productRepository.getProduct(selectedProduct);
                double currentAllPrice = (product.getAllPrice() + Integer.parseInt(productCount));
                OrderItem userOrderList = this.orderRepository.getUserOrderList(chatId);
                if (userOrderList != null) {
                    for (ProductItem productItem : userOrderList.getProductItems()) {
                        if (productItem.getKeyName().equals(product.getKeyName())) {
                            productItem.setAllPrice(productItem.getAllPrice() + currentAllPrice);
                            productItem.setCount(productItem.getCount() + Integer.parseInt(productCount));
                            break;
                        } else {
                            product.setAllPrice(currentAllPrice);
                            product.setCount(Double.parseDouble(productCount));
                            userOrderList.getProductItems().add(product);
                            break;
                        }
                    }
                } else {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setDate(new Date());
                    orderItem.setId(this.orderRepository.orderMap.size() + 1);
                    orderItem.setOrderItemType(OrderItemType.SELECTED);
                    orderItem.setOrderNumber(this.orderRepository.orderMap.size() + 1);
                    orderItem.setUserId(chatId);
//                    orderItem.setUserName();
                    this.orderRepository.addOrder(this.orderRepository.orderMap.size() + 1, orderItem);
                }
                return generalController.getMenu(chatId);
            }
            sendMessage.setParseMode("HTML");
            sendMessage.setChatId(chatId);
            codeMessage.setSendMessage(sendMessage);
            codeMessage.setType(CodeMessageType.MESSAGE);
        }
        return codeMessage;
    }


    private InlineKeyboardMarkup getProductKeyBoard(String productType) {
        List<List<InlineKeyboardButton>> collection = InlineButtonUtil.collection();
        List<InlineKeyboardButton> row = InlineButtonUtil.row();
        if (this.productRepository.productMap.size() > 0) {
            for (ProductItem productItem : this.productRepository.productMap.values()) {
                if (productItem.getCategoryName().equals(productType) && productItem.isActive()) {
                    row.add(InlineButtonUtil
                            .button(productItem.getName(),
                                    "/product/product_list/" + productItem.getKeyName()));
                    collection.add(row);
                    row = new ArrayList<>();
                }
            }
            collection.add(InlineButtonUtil.row(InlineButtonUtil.button("Menyuga qaytish", "menu", ":clipboard:")));
            return InlineButtonUtil.keyboard(collection);
        }
        return InlineButtonUtil.keyboard(
                InlineButtonUtil.collection(
                        InlineButtonUtil.row(InlineButtonUtil.button("Menyuga qaytish", "menu"))
                ));

    }


}
