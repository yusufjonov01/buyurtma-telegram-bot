package gvs.controller;

import gvs.dto.CodeMessage;
import gvs.dto.ProductItem;
import gvs.enums.CodeMessageType;
import gvs.repository.ProductRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class SendOrderController {
    private ProductRepository productRepository;

    public SendOrderController() {
        this.productRepository = productRepository;
    }

    public CodeMessage handle(String text, Long chatId, Integer messageId) {
        CodeMessage codeMessage = new CodeMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (text.equals("send_order")) {
//            sendMessage.setReplyMarkup(this.getSweetProductKeyBoard(this.sweetRepository.getList()));
//            sendMessage.setText("<b>Pechenyelar ro'yhati:</b>");
//            708154659
            int size = this.productRepository.productMap.size();
            List<ProductItem> todoItemList = this.productRepository.getProducts();
            StringBuilder stringBuilder = new StringBuilder("");
            sendMessage.setParseMode("HTML");
            if (todoItemList != null || !todoItemList.isEmpty()) {
                int count = 1;
                double allPrice = 0;
                stringBuilder.append("<b>Buyurtmalar ro'yhati</b>");
                for (ProductItem dto : todoItemList) {
                    stringBuilder.append("\n\n");
                    stringBuilder.append("<b>" + count + "</b>" + ". Mahsulot nomi: <b>" + dto.getName() + "</b>");
                    stringBuilder.append("\n");
                    stringBuilder.append("Narxi: " + dto.getCount() + "*" + dto.getPrice() + " = <b>"
                            + (dto.getPrice() * dto.getCount()) + "</b> so'm");
                    stringBuilder.append("\n");
                    stringBuilder.append("\n\n");
                    allPrice += (dto.getPrice() * dto.getCount());
                    count++;
                }
                stringBuilder.append("Umumiy narx:" + allPrice);
                sendMessage.setText(stringBuilder.toString());
            } else {
                sendMessage.setText("Siz xali mahsulot tanlamadigiz !" + size);
            }
            sendMessage.setChatId(chatId);
            codeMessage.setSendMessage(sendMessage);
            codeMessage.setType(CodeMessageType.MESSAGE);
        }
        return codeMessage;
    }
}
