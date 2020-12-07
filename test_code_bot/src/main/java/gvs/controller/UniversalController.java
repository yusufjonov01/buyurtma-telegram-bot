package gvs.controller;

import com.itextpdf.text.DocumentException;
import gvs.dto.*;
import gvs.enums.CodeMessageType;
import gvs.enums.OrderItemType;
import gvs.enums.ProductItemType;
import gvs.repository.OrderRepository;
import gvs.repository.ProductRepository;
import gvs.repository.UserRepository;
import gvs.service.CreateDocument;
import gvs.util.InlineButtonUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UniversalController extends TelegramLongPollingBot {
    private ProductRepository productRepository = new ProductRepository();
    private GeneralController generalController = new GeneralController();
    private OrderRepository orderRepository = new OrderRepository();
    private UserRepository userRepository = new UserRepository();
    private Map<Integer, List<ProductItem>> productPageMap = new HashMap<>();
    private List<String> aboutList = new ArrayList<>();

    private Map<Long, ProductItem> productTempMap = new HashMap<>();
    private Map<Long, UserItem> userTempMap = new HashMap<>();
    private Map<Long, OrderItem> orderTempMap = new HashMap<>();
    private CreateDocument document = new CreateDocument();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthDateFormat = new SimpleDateFormat("MM.yyyy");
    SimpleDateFormat dayDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public CodeMessage handle(String text, Long chatId, Integer messageId, Message message) throws IOException, DocumentException, TelegramApiException, ParseException {
        CodeMessage codeMessage = new CodeMessage();
        SendMessage sendMessage = new SendMessage();
        SendVideo sendVideo = new SendVideo();
        SendPhoto sendPhoto = new SendPhoto();
        SendDocument sendDocument = new SendDocument();
        sendMessage.setChatId(chatId);


        if (text.startsWith("/universal/")) {
            String[] commandList = text.split("/");
            String command = commandList[2];
            switch (command) {
                case "report":
                    if (commandList.length >= 4) {
                        switch (commandList[3]) {
                            case "reppro":
                                sendMessage.setText("Ҳисобот");
                                List<ProductItem> productItems1 = (List<ProductItem>) this.productRepository.productMap.values();
                                File file = this.document.reportOrderByProduct(productItems1);
                                sendDocument.setDocument(file);
                                sendDocument.setChatId(chatId);
                                codeMessage.setType(CodeMessageType.MESSAGE_FILE);
                                codeMessage.setSendDocument(sendDocument);
                                codeMessage.setSendMessage(sendMessage);
                                return codeMessage;
                            case "product":
                                if (commandList.length > 4) {
                                    ProductItem productItem = new ProductItem();
                                    productItem.setId(this.productRepository.productMap.size() + 1);
                                    productItem.setProductItemType(ProductItemType.REPORT_PRODUCT);
                                    this.productTempMap.put(chatId, productItem);
                                    productItem.setUserId(chatId);
                                    switch (commandList[4]) {
                                        case "year":
                                            sendMessage.setText("Yilni kiriting: \n" +
                                                    "Mislo: 01.01.2020");
                                            break;
                                        case "month":
                                            sendMessage.setText("Oyni kiriting: \n" +
                                                    "Mislo: 01.06.2020");
                                            break;
                                        case "day":
                                            sendMessage.setText("Kunni kiriting: \n" +
                                                    "Mislo: 12.06.2020");
                                            break;

                                    }
                                    codeMessage.setSendMessage(sendMessage);
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    return codeMessage;
                                }
                                sendMessage.setText("Hisobotni turini tanlang: ");
                                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("♒ Yillik ♒", "/universal/report/product/year")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("♒ Oylik ♒", "/universal/report/product/month")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("♒ Kunlik ♒", "/universal/report/product/day")
                                                ),
                                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                        )));
                                break;
                            case "user":
                                if (commandList.length > 4) {
                                    ProductItem productItem = new ProductItem();
                                    productItem.setId(this.productRepository.productMap.size() + 1);
                                    productItem.setProductItemType(ProductItemType.REPORT_USER);
                                    this.productTempMap.put(chatId, productItem);
                                    productItem.setUserId(chatId);
                                    switch (commandList[4]) {
                                        case "year":
                                            sendMessage.setText("Yilni kiriting: \n" +
                                                    "Mislo: 01.01.2020");
                                            break;
                                        case "month":
                                            sendMessage.setText("Oyni kiriting: \n" +
                                                    "Mislo: 01.06.2020");
                                            break;
                                        case "day":
                                            sendMessage.setText("Kunni kiriting: \n" +
                                                    "Mislo: 12.06.2020");
                                            break;

                                    }
                                    codeMessage.setSendMessage(sendMessage);
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    return codeMessage;
                                }
                                sendMessage.setText("Hisobotni turini tanlang: ");
                                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("♒ Yillik ♒", "/universal/report/user/year")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("♒ Oylik ♒", "/universal/report/user/month")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("♒ Kunlik ♒", "/universal/report/user/day")
                                                ),
                                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                        )));
                                break;
                        }
                        codeMessage.setSendMessage(sendMessage);
                        codeMessage.setType(CodeMessageType.MESSAGE);
                        return codeMessage;
                    }
                    sendMessage.setText("Hisobotni turini tanlang: ");
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                            InlineButtonUtil.collection(
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("♒ Маҳсулотлар қолдиғи бўйича  ♒", "/universal/report/reppro")
                                    ),
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("♒ Mahsulotlar bo'yicha sotuv ♒", "/universal/report/product")
                                    ),
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("➕ Mijozlar bo'yicha sotuv ➕", "/universal/report/user")
                                    ),
                                    InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                            )));
                    codeMessage.setSendMessage(sendMessage);
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    return codeMessage;
                case "about":
                    if (commandList.length >= 4 && commandList[3].equals("admin")) {// /universal/about/admin/edit
                        if (commandList.length == 5) {
                            sendMessage.setText("Yangi matnni kirting:");
                            ProductItem productItem = new ProductItem();
                            productItem.setId(this.productRepository.productMap.size() + 1);
                            productItem.setUserId(chatId);
                            productItem.setProductItemType(ProductItemType.ABOUT_EDIT);
                            productItem.setMessage(aboutList.size() > 0 ? aboutList.get(0) : "");
                            this.productTempMap.put(chatId, productItem);
                            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                    InlineButtonUtil.collection(
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("⬅️ Orqaga️", "/universal/about/admin"),
                                                    InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")
                                            )
                                    )));
                            codeMessage.setType(CodeMessageType.MESSAGE);
                            codeMessage.setSendMessage(sendMessage);
                            return codeMessage;
                        }
                        if (aboutList.size() > 0) {
                            sendMessage.setText(aboutList.get(0));
                        } else {
                            sendMessage.setText("Ma'lumot yo'q");
                        }
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("Tahrirlash️", "/universal/about/admin/edit")),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("⬅️ Orqaga️", "/universal/about/admin"),
                                                InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")
                                        )
                                )));

                        codeMessage.setType(CodeMessageType.MESSAGE);
                        codeMessage.setSendMessage(sendMessage);
                        return codeMessage;
                    }
                    if (aboutList.size() > 0) {
                        sendMessage.setText(aboutList.get(0));
                    } else {
                        sendMessage.setText("Ma'lumot yo'q");
                    }
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case "user":// /universal/menu
                    if (commandList.length == 4) { //  /universal/menu/sweet
                        switch (commandList[3]) {
                            case "sweet":
                                sendMessage.setText("<b>Pechenyelar ro'yhati:</b>");
                                break;
                            case "drink":
                                sendMessage.setText("<b>Ichimliklar ro'yhati:</b>");
                                break;
                            case "diaper":
                                sendMessage.setText("<b>Tagliklar ro'yhati:</b>");
                                break;
                        }
                        sendMessage.setParseMode("HTML");
                        sendMessage.setReplyMarkup(this.getProductKeyBoard(commandList[3], "user"));
                        if (productRepository.productMap != null) {
                            System.out.println(productRepository.productMap.values());
                        } else {
                            System.out.println("Bo'sh");
                        }
                        codeMessage.setSendMessage(sendMessage);
                        codeMessage.setType(CodeMessageType.MESSAGE);
                    } else {
                        EditMessageText editMessageText = new EditMessageText();
                        editMessageText.setText("*Menyu*");
                        editMessageText.setChatId(chatId);
                        editMessageText.setMessageId(messageId);
                        editMessageText.setParseMode("Markdown");
                        editMessageText.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("Pechenyelar", "/universal/user/sweet", ":cake:"),
                                                InlineButtonUtil.button("Ichimliklar", "/universal/user/drink", ":cocktail:")

                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("Tagliklar", "/universal/user/diaper", ":baby:"),
                                                InlineButtonUtil.button("Savat", "/universal/basket", ":pouch:")
                                        )
                                )));
                        codeMessage.setType(CodeMessageType.EDIT);
                        codeMessage.setEditMessageText(editMessageText);
                    }
                    return codeMessage;
                case "adminPanel":
                    if (commandList.length == 4) {
                        switch (commandList[3]) {
                            case "product":
                                EditMessageText editMessageText = new EditMessageText();
                                editMessageText.setText("* ---- Mahsuot menyu ---- *");
                                editMessageText.setChatId(chatId);
                                editMessageText.setMessageId(messageId);
                                editMessageText.setParseMode("Markdown");
                                editMessageText.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("♒ Ro'yhati ♒", "/universal/product/list")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("➕ Qo'shish ➕", "/universal/product/add")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("➕ File orqali qo'shish ➕", "/universal/product/addByFile")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("✏️ Tahrirlash ✏️", "/universal/product/update")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("\uD83D\uDDD1️ O'chirish \uD83D\uDDD1️", "/universal/product/delete")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("\t\uD83D\uDC40 Ko'rish \t\uD83D\uDC40", "/universal/product/see")
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("✳️ Savdoga qo'yish/olish ✴️", "/universal/product/activate")
                                                ),
                                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                        )));
                                codeMessage.setType(CodeMessageType.EDIT);
                                codeMessage.setEditMessageText(editMessageText);
                                break;
                            case "report":

                                break;
                        }
                        return codeMessage;
                    }
                    return this.generalController.getAdminMenu(messageId, chatId);
                case "product":
                    if (commandList.length == 4 || commandList.length == 5 || commandList.length >= 6) {
                        switch (commandList[3]) {
                            case "sellType":
                                if (commandList.length == 6) {
                                    ProductItem productItem = productTempMap.get(chatId);
                                    if (productItem.getProductSellType() != null) {
                                        sendMessage.setText("Siz allaqachon tanlab bo'lgansiz!");
                                    } else {
                                        if (commandList[4].equals("dona")) {
                                            productItem.setProductSellType("dona");
                                            productItem.setProductItemType(ProductItemType.COUNT);
                                            productTempMap.put(chatId, productItem);
                                        } else if (commandList[4].equals("blok")) {
                                            productItem.setProductSellType("blok");
                                            productItem.setProductItemType(ProductItemType.COUNT);
                                            productTempMap.put(chatId, productItem);
                                        } else if (commandList[4].equals("all")) {
                                            productItem.setProductSellType("all");
                                            productItem.setProductItemType(ProductItemType.COUNT);
                                            productTempMap.put(chatId, productItem);
                                        } else {
                                            sendMessage.setText("Xatolik!");
                                        }

                                    }
                                    String sellType = "";
                                    switch (productItem.getProductSellType()) {
                                        case "dona":
                                            sellType = "donalab";
                                            break;
                                        case "blok":
                                            sellType = "karobka";
                                            break;
                                        case "all":
                                            sellType = "donaga, karobkaga";
                                            break;
                                    }
                                    if (productItem.getProductSellType() != null) {
                                        sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                                "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                                                "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n" +
                                                "💵 Eng kam sotiladigan karobkadagi narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + " so'm*\n" +
                                                "📦 Miqdori: *" + String.format("%,.2f", productItem.getAmount()) + " "
                                                + productItem.getWeightType() + "*\n" +
                                                "📦 Eng kam sotiladigan karobkadagi miqdori: *" +
                                                String.format("%,.0f", productItem.getAllAmount()) + " " + productItem.getWeightType() + "*\n" +
                                                "🗂️ Sotish turi: *" + sellType + "*\n\n\n" +
                                                "Mahsulot umumiy sonini jo'nating(karobka):");
                                    } else {
                                        sendMessage.setText("Xatolik");
                                    }
                                } else {
                                    sendMessage.setText("Xatolik!");
                                }
                                sendMessage.setParseMode("Markdown");
                                codeMessage.setType(CodeMessageType.MESSAGE);
                                codeMessage.setSendMessage(sendMessage);
                                return codeMessage;
                            case "addByFile":
                                sendMessage.setText("Excel formatda mahsulotlar ro'yhatini yuboring:");
                                ProductItem productItem = new ProductItem();
                                productItem.setId(this.productRepository.productMap.size() + 1);
                                productItem.setUserId(chatId);
                                productItem.setProductItemType(ProductItemType.ADD_FILE);
                                this.productTempMap.put(chatId, productItem);

                                codeMessage.setType(CodeMessageType.MESSAGE);
                                codeMessage.setSendMessage(sendMessage);
                                return codeMessage;
                            case "add":
                                if (commandList.length == 6) {
                                    productItem = new ProductItem();
                                    for (ProductItem productItem1 : this.productTempMap.values()) {
                                        if (productItem1.getProductItemType().equals(ProductItemType.CATEGORY_NAME)) {
                                            productItem = productItem1;
                                            break;
                                        }
                                    }
                                    if (productItem != null) {
                                        String categoryName = "";
                                        switch (commandList[5]) {
                                            case "sweet":
                                                categoryName = "pechenye";
                                                break;
                                            case "drink":
                                                categoryName = "ichimlik";
                                                break;
                                            case "diaper":
                                                categoryName = "taglik";
                                                break;
                                        }
                                        sendMessage.setParseMode("Markdown");
                                        productItem.setCategoryName(commandList[5]);
                                        sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                                "🗂️ Bo'lim: *" + categoryName + "*\n\n\n" +
                                                "*Mahsulot narxini jo'nating(so'mda):*\n" +
                                                "(Misol: *3000*)");
                                        productItem.setProductItemType(ProductItemType.PRICE);
                                    } else {
                                        sendMessage.setText("Xatolik!");
                                    }
                                    codeMessage.setSendMessage(sendMessage);
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    return codeMessage;

                                }
                                sendMessage.setChatId(chatId);
                                sendMessage.setText("*Mahsulot nomi yozing:*");
                                sendMessage.setParseMode("Markdown");

                                productItem = new ProductItem();
                                productItem.setId(this.productRepository.productMap.size() + 1);
                                productItem.setUserId(chatId);
                                productItem.setProductItemType(ProductItemType.NAME);
                                this.productTempMap.put(chatId, productItem);

                                codeMessage.setSendMessage(sendMessage);
                                codeMessage.setType(CodeMessageType.MESSAGE);
                                return codeMessage;
                            case "list":
                                if (commandList.length == 5) {
                                    switch (commandList[4]) {
                                        case "sweet":
                                            sendMessage.setText("<b>Pechenyelar ro'yhati:</b>");
                                            break;
                                        case "drink":
                                            sendMessage.setText("<b>Ichimliklar ro'yhati:</b>");
                                            break;
                                        case "diaper":
                                            sendMessage.setText("<b>Tagliklar ro'yhati:</b>");
                                            break;
                                    }
                                    sendMessage.setParseMode("HTML");
                                    sendMessage.setReplyMarkup(this.getProductKeyBoard(commandList[4], "admin"));
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    codeMessage.setSendMessage(sendMessage);
                                    return codeMessage;
                                } else {
                                    sendMessage.setText("*Menyu*");
                                    sendMessage.setChatId(chatId);
                                    sendMessage.setParseMode("Markdown");

                                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                            InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("\t\uD83E\uDDC7 Pechenyelar \t\uD83E\uDDC7", "/universal/product/list/sweet"),
                                                            InlineButtonUtil.button("\uD83E\uDD64 Ichimliklar \uD83E\uDD64", "/universal/product/list/drink")

                                                    ),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("\t\uD83D\uDC76 Tagliklar \t\uD83D\uDC76", "/universal/product/list/diaper")
                                                    ),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("⬅️ Menyuga qaytish", "/adminPanel")
                                                    )
                                            )));

                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    codeMessage.setSendMessage(sendMessage);
                                    return codeMessage;

                                }
                            case "select":
                                String selectedProduct = commandList[4];
                                ProductItem product = this.productRepository.getProduct(selectedProduct);
                                sendPhoto.setChatId(chatId);
                                sendPhoto.setPhoto(product.getPhotoId());

                                sendPhoto.setCaption("* ---- Mahsulot ---- *\n\n" +
                                        "🔠 Nomi: *" + product.getName() + "*\n" +
                                        "🔑 Kalit so'z: *" + product.getKeyName() + "*\n" +
                                        "🗂️ Bo'lim: *" + product.getCategoryName() + "*\n" +
                                        "🗂️ Sotish turi: *" + this.getProductSellType(product) + "*\n" +
                                        "\t\uD83D\uDD22 Soni: *" +
                                        String.format("%,.2f", product.getCount() / (product.getAllAmount() / product.getAmount())) + " ta (karobkada), " +
                                        String.format("%,.0f", product.getCount()) + " ta (donada) "
                                        + "*\n" +
                                        "📦 Miqdori: *" + (String.format("%,.2f", product.getAmount()) + " " + product.getWeightType()) + " *\n" +
                                        "📦 Eng kam sotiladigan karobkadagi miqdori: *" + (String.format("%,.2f", product.getAllAmount()) + " " + product.getWeightType()) + " *\n" +
                                        "💵 Narxi: *" + (String.format("%,.2f", product.getPrice())) + " so'm*\n" +
                                        "💵 Eng kam sotiladigan karobkadagi narxi: *" + (String.format("%,.2f", product.getAllPrice())) + " so'm*\n" +
                                        "\t\uD83C\uDD97 Holati: *" + (product.isActive() ? " savdoda" : "savdoda emas") + "*"
                                );
                                sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("\t\uD83D\uDCDD Tahrirlash \t\uD83D\uDCDD", "/universal/product/update/content/" + product.getKeyName())
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("\t\uD83D\uDD12 Holatini o'zgartirish \t\uD83D\uDD13", "/universal/product/activate/" + product.getKeyName())
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("\uD83D\uDDD1️ O'chirish \uD83D\uDDD1️", "/universal/product/delete/" + product.getKeyName())
                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("⬅️️ Orqaga", "/universal/product/list"),
                                                        InlineButtonUtil.button("✅ Menu ✅", "/adminPanel")
                                                )
                                        )));
                                sendPhoto.setParseMode("Markdown");
                                codeMessage.setType(CodeMessageType.PHOTO);
                                codeMessage.setSendPhoto(sendPhoto);
                                return codeMessage;
                            case "delete":
                                if (commandList.length == 6) {
                                    if (commandList[5].equals("ha")) {
                                        this.productRepository.productMap.remove(commandList[4]);
                                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                                InlineButtonUtil.collection(
                                                        InlineButtonUtil.row(
                                                                InlineButtonUtil.button("Mahsulotlar", "/universal/product/list")
                                                        ),
                                                        InlineButtonUtil.row(InlineButtonUtil.button("⬅️️ Menyu", "/adminPanel"))
                                                )));
                                        sendMessage.setText("*Mahsulot o'chirildi!*");
                                        sendMessage.setParseMode("Markdown");
                                        codeMessage.setType(CodeMessageType.MESSAGE);
                                        codeMessage.setSendMessage(sendMessage);
                                        return codeMessage;
                                    }
                                }
                                if (commandList.length == 5) {
                                    ProductItem deleteProduct = this.productRepository.getProduct(commandList[4]);
                                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                            InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("✅️️️ Ha ✅", "/universal/product/delete/" + deleteProduct.getKeyName() + "/ha")
                                                    ),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("❌ Yo'q ❌", "/universal/product/select/" + deleteProduct.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("⬅️️ Orqaga", "/universal/product/select/" + deleteProduct.getKeyName()),
                                                            InlineButtonUtil.button("✅ Menu ✅", "/universal/product")
                                                    )
                                            )));
                                    sendMessage.setParseMode("Markdown");
                                    sendMessage.setText(
                                            "Siz *" + deleteProduct.getName() + "* nomli,\n" +
                                                    "*" + deleteProduct.getKeyName() + "* raqamli, mahsulotni o'chirishni istaysizmi?\n");
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    codeMessage.setSendMessage(sendMessage);
                                    return codeMessage;
                                }
                                sendMessage.setChatId(chatId);
                                sendMessage.setText("*Mahsulot nomi yozing:*");
                                sendMessage.setParseMode("Markdown");

                                productItem = new ProductItem();
                                productItem.setId(this.productRepository.productMap.size() + 1);
                                productItem.setUserId(chatId);
                                productItem.setProductItemType(ProductItemType.DELETE);
                                this.productTempMap.put(chatId, productItem);

                                codeMessage.setSendMessage(sendMessage);
                                codeMessage.setType(CodeMessageType.MESSAGE);
                                return codeMessage;

                            case "update":
                                if (commandList.length == 4) {
                                    sendMessage.setText("\n\n*Mahsulot kodini kiriting:*\n\n");
                                    sendMessage.setParseMode("Markdown");
                                    sendMessage.setChatId(chatId);

                                    ProductItem productTemp = new ProductItem();
                                    productTemp.setId(this.productRepository.productMap.size() + 1);
                                    productTemp.setUserId(chatId);
                                    productTemp.setProductItemType(ProductItemType.UPDATE_SELECT);
                                    this.productTempMap.put(chatId, productTemp);

                                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                            InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product")
                                                    ),
                                                    InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                            )));
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    codeMessage.setSendMessage(sendMessage);
                                    return codeMessage;
                                } else if (commandList.length >= 6) { // /universal/product/update/content/fr
                                    productItem = this.productRepository.productMap.get(commandList[5]);
                                    switch (commandList[4]) {
                                        case "content":
                                            sendMessage.setParseMode("Markdown");
                                            String sellType = this.getProductSellType(productItem);
                                            sendMessage.setText("* ---- 📝 Tahrirlanayotgan mahsulot 	📝 ---- *\n" +
                                                    "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                                    "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                                    "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                                                    "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n" +
                                                    "\t\uD83D\uDD22 Soni: *" +
                                                    String.format("%,.2f", productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount())) + " ta (karobkada), " +
                                                    String.format("%,.0f", productItem.getCount()) + " ta (donada) "
                                                    + "*\n" +
                                                    "💵 Eng kam sotiladigan karobkadagi narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + " so'm*\n" +
                                                    "📦 Miqdori: *" + String.format("%,.2f", productItem.getAmount()) + " "
                                                    + productItem.getWeightType() + "*\n" +
                                                    "📦 Eng kam sotiladigan karobkadagi miqdori: *" +
                                                    String.format("%,.2f", productItem.getAllAmount()) + " " + productItem.getWeightType() + "*\n" +
                                                    "🗂️ Sotish turi: *" + sellType + "*\n\n\n" +
                                                    "Tahrir qilish kerak bo'lgan qismni tanlang:");
                                            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("🔠 Nomi 🔠", "/universal/product/update/name/" + productItem.getKeyName()),
                                                            InlineButtonUtil.button("🗂️️️ Bo'limi 🗂️", "/universal/product/update/category/" + productItem.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("💵 Narxi 💵", "/universal/product/update/price/" + productItem.getKeyName()),
                                                            InlineButtonUtil.button("💵️️ Umumiy narxi 💵️", "/universal/product/update/allprice/" + productItem.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("📦 Midori 📦", "/universal/product/update/amount/" + productItem.getKeyName()),
                                                            InlineButtonUtil.button("📦️️ Umumiy miqdori 📦️", "/universal/product/update/allamount/" + productItem.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("🗂️ Sotish turi 🗂️", "/universal/product/update/selltype/" + productItem.getKeyName()),
                                                            InlineButtonUtil.button("🗂️ Miqdori turi 🗂️", "/universal/product/update/weighttype/" + productItem.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("⬅️️ Orqaga️", "/universal/product/update/" + productItem.getKeyName()),
                                                            InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                            )));
                                            codeMessage.setType(CodeMessageType.MESSAGE);
                                            codeMessage.setSendMessage(sendMessage);
                                            return codeMessage;
                                        case "photo":
                                            sendMessage.setText("*Mahsulot yangi rasmini kiriting:*");
                                            productItem.setProductItemType(ProductItemType.UPDATE_PHOTO);
                                            break;
                                        case "price":
                                            sendMessage.setText("Yangi narxni kiriting:");
                                            productItem.setProductItemType(ProductItemType.UPDATE_PRICE);
                                            break;
                                        case "allprice":
                                            sendMessage.setText("Yangi umumiy narxni kiriting:");
                                            productItem.setProductItemType(ProductItemType.UPDATE_ALL_PRICE);
                                            break;
                                        case "amount":
                                            sendMessage.setText("Yangi miqdorni kiriting:");
                                            productItem.setProductItemType(ProductItemType.UPDATE_AMOUNT);
                                            break;
                                        case "allamount":
                                            sendMessage.setText("Yangi umumiy miqdorni kiriting:");
                                            productItem.setProductItemType(ProductItemType.UPDATE_ALL_AMOUNT);
                                            break;
                                        case "weighttype":
                                            sendMessage.setText("Yangi miqdorini tuyini kiriting:");
                                            productItem.setProductItemType(ProductItemType.UPDATE_WEIGHT_TYPE);
                                            break;
                                        case "selltype":
                                            if (commandList.length == 7) {
                                                ProductItem productItem1 = this.productRepository.productMap.get(commandList[6]);
                                                if (productItem1 != null) {
                                                    if (productItem1.getProductItemType().equals(ProductItemType.UPDATE_SELL_TYPE)) {
                                                        switch (commandList[5]) {
                                                            case "dona":
                                                                productItem1.setProductSellType("dona");
                                                                break;
                                                            case "blok":
                                                                productItem1.setProductSellType("blok");
                                                                break;
                                                            case "all":
                                                                productItem1.setProductSellType("all");
                                                                break;
                                                        }
                                                        sendMessage.setText("Mahsulot tahrirlandi!");
                                                        productItem1.setProductItemType(ProductItemType.UPDATE_CONTENT);
                                                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                                                InlineButtonUtil.collection(
                                                                        InlineButtonUtil.row(
                                                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem1.getKeyName())
                                                                        ),
                                                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                                                )));
                                                        codeMessage.setSendMessage(sendMessage);
                                                        codeMessage.setType(CodeMessageType.MESSAGE);
                                                        return codeMessage;
                                                    }
                                                }
                                            }
                                            sendMessage.setText("Yangi sotilish turini  tanlang:");
                                            productItem.setProductItemType(ProductItemType.UPDATE_SELL_TYPE);
                                            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button("📦 Donalab 📦", "/universal/product/update/selltype/dona/" + productItem.getKeyName()),
                                                            InlineButtonUtil.button("📦 Karobkaga 📦️", "/universal/product/update/selltype/blok/" + productItem.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(InlineButtonUtil.button("️️️📦 Ikkalasi ham 📦", "/universal/product/update/selltype/all/" + productItem.getKeyName())),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                            )));
                                            codeMessage.setSendMessage(sendMessage);
                                            codeMessage.setType(CodeMessageType.MESSAGE);
                                            return codeMessage;
                                        case "name":
                                            sendMessage.setText("Yangi nomini kiriting:");
                                            productItem.setProductItemType(ProductItemType.UPDATE_NAME);
                                            break;
                                        case "category":
                                            if (commandList.length == 7) {
                                                ProductItem productItem1 = this.productRepository.productMap.get(commandList[6]);
                                                if (productItem1 != null) {
                                                    if (productItem1.getProductItemType().equals(ProductItemType.UPDATE_CATEGORY)) {
                                                        switch (commandList[5]) {
                                                            case "sweet":
                                                                productItem1.setCategoryName("sweet");
                                                                break;
                                                            case "drink":
                                                                productItem1.setCategoryName("drink");
                                                                break;
                                                            case "diaper":
                                                                productItem1.setCategoryName("diaper");
                                                                break;
                                                        }
                                                        sendMessage.setText("Mahsulot tahrirlandi!");
                                                        productItem1.setProductItemType(ProductItemType.UPDATE_CONTENT);
                                                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                                                InlineButtonUtil.collection(
                                                                        InlineButtonUtil.row(
                                                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem1.getKeyName())
                                                                        ),
                                                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                                                )));
                                                        codeMessage.setSendMessage(sendMessage);
                                                        codeMessage.setType(CodeMessageType.MESSAGE);
                                                        return codeMessage;
                                                    }
                                                }
                                            }
                                            sendMessage.setText("Yangi bo'limni kiriting:");
                                            productItem.setProductItemType(ProductItemType.UPDATE_CATEGORY);
                                            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button(" Pechenye ", "/universal/product/update/category/sweet/" + productItem.getKeyName(), ":cake:"),
                                                            InlineButtonUtil.button(" Ichimliklar ", "/universal/product/update/category/drink/" + productItem.getKeyName(), ":cocktail:")
                                                    ),
                                                    InlineButtonUtil.row(InlineButtonUtil.button("️️️Tagliklar ", "/universal/product/update/category/diaper/" + productItem.getKeyName(), ":baby:")),
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                            )));
                                            codeMessage.setSendMessage(sendMessage);
                                            codeMessage.setType(CodeMessageType.MESSAGE);
                                            return codeMessage;
                                    }
                                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                            InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                                    ),
                                                    InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                            )));

                                    this.productTempMap.put(chatId, productItem);
                                    sendMessage.setParseMode("Markdown");
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    codeMessage.setSendMessage(sendMessage);
                                    return codeMessage;
                                }
                                break;
                            case "activate":
                                if (commandList.length == 4) {
                                    sendMessage.setText("*Mahsulot kodini kiriting:*");
                                    sendMessage.setParseMode("Markdown");
                                    sendMessage.setChatId(chatId);

                                    ProductItem productTemp = new ProductItem();
                                    productTemp.setId(this.productRepository.productMap.size() + 1);
                                    productTemp.setUserId(chatId);
                                    productTemp.setProductItemType(ProductItemType.UPDATE_ACTIVATE);
                                    this.productTempMap.put(chatId, productTemp);

                                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                            InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product")
                                                    ),
                                                    InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                            )));
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    codeMessage.setSendMessage(sendMessage);
                                    return codeMessage;
                                } else if (commandList.length == 5) {
                                    boolean b = this.productRepository.changeProductActive(commandList[4]);
                                    this.productTempMap.remove(chatId);
                                    sendMessage.setText((b ? "*Amal bajarildi!*" : "*Xatolik! Qaytadan urinib ko'ring!*"));
                                    sendMessage.setParseMode("Markdown");
                                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                            InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(
                                                            InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product")
                                                    ),
                                                    InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                            )));
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    codeMessage.setSendMessage(sendMessage);
                                    return codeMessage;
                                }
                                break;
                            case "see":
                                sendMessage.setText("*Mahsulot kodini kiriting:*");
                                sendMessage.setParseMode("Markdown");
                                sendMessage.setChatId(chatId);

                                ProductItem productTemp = new ProductItem();
                                productTemp.setId(this.productRepository.productMap.size() + 1);
                                productTemp.setUserId(chatId);
                                productTemp.setProductItemType(ProductItemType.SEE);
                                this.productTempMap.put(chatId, productTemp);

                                codeMessage.setType(CodeMessageType.MESSAGE);
                                codeMessage.setSendMessage(sendMessage);
                                return codeMessage;
                        }
                    }
                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setText("* ---- Admin menyu ---- *");
                    editMessageText.setChatId(chatId);
                    editMessageText.setMessageId(messageId);
                    editMessageText.setParseMode("Markdown");
                    editMessageText.setReplyMarkup(InlineButtonUtil.keyboard(
                            InlineButtonUtil.collection(
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("♒ Ro'yhati ♒", "/universal/product/list")
                                    ),
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("➕ Qo'shish ➕", "/universal/product/add")
                                    ),
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("➕ File orqali qo'shish ➕", "/universal/product/addByFile")
                                    ),
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("✏️ Tahrirlash ✏️", "/universal/product/update")
                                    ),
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("\uD83D\uDDD1️ O'chirish \uD83D\uDDD1️", "/universal/product/delete")
                                    ),
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("\t\uD83D\uDC40 Ko'rish \t\uD83D\uDC40", "/universal/product/see")
                                    ),
                                    InlineButtonUtil.row(
                                            InlineButtonUtil.button("✳️ Savdoga qo'yish/olish ✴️", "/universal/product/activate")
                                    ),
                                    InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                            )));

                    codeMessage.setType(CodeMessageType.EDIT);
                    codeMessage.setEditMessageText(editMessageText);
                    return codeMessage;
                case "order":// buyurtmalarni adminga jo'natish uchun
                    // /universal/order/product/F33/2
                    // /universal/order/getLocation
                    // /universal/order/confirmed/23
                    if (commandList.length >= 5) {
                        if (commandList[3].equals("product")) {
                            if (commandList.length == 7) {
                                String orderProductType = commandList[4];
                                int count = Integer.parseInt(commandList[6]);
                                ProductItem product = this.productRepository.getProduct(commandList[5]);
                                double currentAllPrice = 0;
                                if (orderProductType.equals("blok")) {
                                    currentAllPrice = (product.getAllPrice() * count);
                                } else {
                                    currentAllPrice = (product.getPrice() * count);
                                }
                                OrderItem userOrderList = this.orderRepository.getUserOrderList(chatId);
                                if (userOrderList != null) {
                                    Boolean isHave = null;
                                    for (ProductItem productItem : userOrderList.getProductItems()) {
                                        if (productItem.getKeyName().equals(product.getKeyName())) {
                                            productItem.setTotalPrice(productItem.getTotalPrice() + currentAllPrice);
                                            if (orderProductType.equals("blok")) {
                                                productItem.setCount(productItem.getCount() + ((product.getAllAmount() / product.getAmount()) * count));
                                            } else {
                                                productItem.setCount(productItem.getCount() + count);
                                            }
                                            userOrderList.setProductItems(userOrderList.getProductItems());
                                            this.orderRepository.addOrder(userOrderList.getOrderNumber(), userOrderList);
                                            return generalController.getMenu(chatId);
                                        }
                                        isHave = false;
                                    }
                                    if (isHave == false) {
                                        ProductItem orderProduct = new ProductItem();
                                        orderProduct.setKeyName(product.getKeyName());
                                        orderProduct.setName(product.getName());
                                        orderProduct.setCategoryName(product.getCategoryName());
                                        orderProduct.setWeightType(product.getWeightType());
                                        orderProduct.setAllAmount(product.getAllAmount());
                                        orderProduct.setAmount(product.getAmount());
                                        orderProduct.setPrice(product.getPrice());

                                        if (orderProductType.equals("blok")) {
                                            orderProduct.setCount((product.getAllAmount() / product.getAmount()) * count);
                                        } else {
                                            orderProduct.setCount(count);
                                        }

                                        orderProduct.setTotalPrice(currentAllPrice);
                                        orderProduct.setAllPrice(product.getAllPrice());
                                        orderProduct.setOrderProductType(orderProductType);

                                        List<ProductItem> productItems = userOrderList.getProductItems();
                                        productItems.add(orderProduct);

                                        userOrderList.setProductItems(productItems);
                                        this.orderRepository.addOrder(userOrderList.getOrderNumber(), userOrderList);
                                        return generalController.getMenu(chatId);

                                    }
                                } else {
                                    OrderItem orderItem = new OrderItem();
                                    orderItem.setId(this.orderRepository.orderMap.size() + 1);
                                    orderItem.setOrderItemType(OrderItemType.SELECTED);
                                    orderItem.setOrderNumber(this.orderRepository.orderMap.size() + 1);
                                    orderItem.setUserId(chatId);

                                    List<ProductItem> productItems = new ArrayList<>();
                                    ProductItem orderProduct = new ProductItem();
                                    orderProduct.setKeyName(product.getKeyName());
                                    orderProduct.setName(product.getName());
                                    orderProduct.setCategoryName(product.getCategoryName());
                                    orderProduct.setWeightType(product.getWeightType());
                                    orderProduct.setAllAmount(product.getAllAmount());
                                    orderProduct.setAmount(product.getAmount());
                                    orderProduct.setPrice(product.getPrice());

                                    if (orderProductType.equals("blok")) {
                                        orderProduct.setCount((product.getAllAmount() / product.getAmount()) * count);
                                    } else {
                                        orderProduct.setCount(count);
                                    }

                                    orderProduct.setTotalPrice(currentAllPrice);
                                    orderProduct.setAllPrice(product.getAllPrice());
                                    orderProduct.setOrderProductType(orderProductType);

                                    productItems.add(orderProduct);
                                    orderItem.setProductItems(productItems);
                                    this.orderRepository.addOrder(this.orderRepository.orderMap.size() + 1, orderItem);
                                    return generalController.getMenu(chatId);
                                }
                            } else {
                                String selectedProduct = commandList[4];
                                ProductItem product = this.productRepository.getProduct(selectedProduct);
                                if (product != null) {
                                    if (product.getProductSellType().equals("all")) {
                                        if (commandList.length == 6) {
                                            sendMessage.setParseMode("Markdown");
                                            sendMessage.setText("\uD83D\uDD20 __Mahsulot nomi__: *" + product.getName() + "*\n" +
                                                    "\t\uD83D\uDDF3️ __Donaga miqdori__: *" + String.format("%,.2f", product.getAmount()) + " " + product.getWeightType() + "*\n" +
                                                    "\uD83D\uDCB5 __Narxi__: *" + String.format("%,.2f", product.getPrice()) + "so'm * \n" +
                                                    "\uD83D\uDCE6 __Blokdagi miqdori__: *" + String.format("%,.2f", product.getAllAmount()) + " " + product.getWeightType() + "*\n" +
                                                    "\t\uD83D\uDCB0 __Blokdagi narxi__: *" + String.format("%,.2f", product.getAllPrice()) + "so'm * \n\n" +
                                                    "\uD83D\uDCCB *Quyida buyurtmangiz " + commandList[5] + "dagi sonini tanlang:*");
                                            sendMessage.setReplyMarkup(resButton("/universal/" + command + "/" + commandList[3] + "/" + commandList[5] + "/", commandList[4]));
                                            if (product.getPhotoId() != null) {
                                                if (product.getPhotoId().length() > 0) {
                                                    sendPhoto.setPhoto(product.getPhotoId());
                                                    sendPhoto.setChatId(chatId);
                                                    sendPhoto.setParseMode("Markdown");
                                                    sendPhoto.setCaption("\uD83D\uDD20 *Mahsulot nomi*: _" + product.getName() + "_\n" +
                                                            "\t\uD83D\uDDF3️ *Donaga miqdori:* _" + String.format("%,.2f", product.getAmount()) + " " + product.getWeightType() + "_\n" +
                                                            "\uD83D\uDCB5 *Narxi:* _" + String.format("%,.2f", product.getPrice()) + "so'm _ \n" +
                                                            "\uD83D\uDCE6 *Blokdagi miqdori:* _" + String.format("%,.2f", product.getAllAmount()) + " " + product.getWeightType() + "_\n" +
                                                            "\t\uD83D\uDCB0 *Blokdagi narxi:* _" + String.format("%,.2f", product.getAllPrice()) + "so'm _ \n\n" +
                                                            "\uD83D\uDCCB *Quyida buyurtmangiz " + commandList[5] + "dagi sonini tanlang:*");
                                                    codeMessage.setType(CodeMessageType.PHOTO);
                                                    codeMessage.setSendPhoto(sendPhoto);
                                                    sendPhoto.setReplyMarkup(resButton("/universal/" + command + "/" + commandList[3] + "/" + commandList[5] + "/", commandList[4]));
                                                    return codeMessage;
                                                }
                                            }
                                        } else {
                                            sendMessage.setParseMode("Markdown");
                                            sendMessage.setText("\uD83D\uDD20 *Mahsulot nomi*: _" + product.getName() + "_\n" +
                                                    "\t\uD83D\uDDF3️ *Donaga miqdori:* _" + String.format("%,.2f", product.getAmount()) + " " + product.getWeightType() + "_\n" +
                                                    "\uD83D\uDCB5 *Narxi:* _" + String.format("%,.2f", product.getPrice()) + "so'm _ \n" +
                                                    "\uD83D\uDCE6 *Blokdagi miqdori:* _" + String.format("%,.2f", product.getAllAmount()) + " " + product.getWeightType() + "_\n" +
                                                    "\t\uD83D\uDCB0 *Blokdagi narxi:* _" + String.format("%,.2f", product.getAllPrice()) + "so'm _ \n" +
                                                    "\uD83D\uDCCB *Quyida buyurtmangiz turini tanlang:*");
                                            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                                    InlineButtonUtil.collection(
                                                            InlineButtonUtil.row(InlineButtonUtil.button("📦 Dona 📦️", "/universal/" + command + "/" + commandList[3]
                                                                    + "/" + commandList[4] + "/dona")),
                                                            InlineButtonUtil.row(InlineButtonUtil.button("📦 Blok 📦️", "/universal/" + command + "/" + commandList[3]
                                                                    + "/" + commandList[4] + "/blok")),
                                                            InlineButtonUtil.row(InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/user/" + product.getCategoryName()),
                                                                    InlineButtonUtil.button("❇️️️ Menu ❇️️️", "menu"))
                                                    )));
                                            if (product.getPhotoId() != null) {
                                                if (product.getPhotoId().length() > 0) {
                                                    sendPhoto.setPhoto(product.getPhotoId());
                                                    sendPhoto.setChatId(chatId);
                                                    sendPhoto.setParseMode("Markdown");
                                                    sendPhoto.setCaption(" \uD83D\uDD20 *Mahsulot nomi*: _" + product.getName() + "_\n" +
                                                            "\t\uD83D\uDDF3️ *Donaga miqdori:* _" + String.format("%,.2f", product.getAmount()) + " " + product.getWeightType() + "_\n" +
                                                            "\uD83D\uDCB5 *Narxi:* _" + String.format("%,.2f", product.getPrice()) + "so'm _ \n" +
                                                            "\uD83D\uDCE6 *Blokdagi miqdori:* _" + String.format("%,.2f", product.getAllAmount()) + " " + product.getWeightType() + "_\n" +
                                                            "\t\uD83D\uDCB0 *Blokdagi narxi:* _" + String.format("%,.2f", product.getAllPrice()) + "so'm _ \n" +
                                                            "\uD83D\uDCCB *Quyida buyurtmangiz turini tanlang:*");
                                                    codeMessage.setType(CodeMessageType.PHOTO);
                                                    codeMessage.setSendPhoto(sendPhoto);
                                                    sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(
                                                            InlineButtonUtil.collection(
                                                                    InlineButtonUtil.row(InlineButtonUtil.button("📦 Dona 📦️", "/universal/" + command + "/" + commandList[3]
                                                                            + "/" + commandList[4] + "/dona")),
                                                                    InlineButtonUtil.row(InlineButtonUtil.button("📦 Blok 📦️", "/universal/" + command + "/" + commandList[3]
                                                                            + "/" + commandList[4] + "/blok")),
                                                                    InlineButtonUtil.row(InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/user/" + product.getCategoryName()),
                                                                            InlineButtonUtil.button("❇️️️ Menu ❇️️️", "menu"))
                                                            )));
                                                    return codeMessage;
                                                }
                                            }
                                        }
                                    } else {
                                        String sellType = product.getProductSellType();
                                        sendMessage.setParseMode("Markdown");
                                        sendMessage.setText("\uD83D\uDD20 __Mahsulot nomi__: *" + product.getName() + "*\n" +
                                                "\t\uD83D\uDDF3️ __Donaga miqdori__: *" + String.format("%,.2f", product.getAmount()) + " " + product.getWeightType() + "*\n" +
                                                "\uD83D\uDCB5 __Narxi__: *" + String.format("%,.2f", product.getPrice()) + "so'm * \n" +
                                                "\uD83D\uDCE6 __Blokdagi miqdori__: *" + String.format("%,.2f", product.getAllAmount()) + " " + product.getWeightType() + "*\n" +
                                                "\t\uD83D\uDCB0 __Blokdagi narxi__: *" + String.format("%,.2f", product.getAllPrice()) + "so'm * \n\n" +
                                                "\uD83D\uDCCB *Quyida buyurtmangiz " + sellType + "dagi sonini tanlang:*");
                                        sendMessage.setReplyMarkup(resButton("/universal/" + command + "/" + commandList[3] + "/" + sellType + "/", commandList[4]));
                                        if (product.getPhotoId() != null) {
                                            if (product.getPhotoId().length() > 0) {
                                                sendPhoto.setPhoto(product.getPhotoId());
                                                sendPhoto.setChatId(chatId);
                                                sendPhoto.setParseMode("Markdown");
                                                sendPhoto.setCaption("\uD83D\uDD20 *Mahsulot nomi*: _" + product.getName() + "_\n" +
                                                        "\t\uD83D\uDDF3️ *Donaga miqdori:* _" + String.format("%,.2f", product.getAmount()) + " " + product.getWeightType() + "_\n" +
                                                        "\uD83D\uDCB5 *Narxi:* _" + String.format("%,.2f", product.getPrice()) + "so'm _ \n" +
                                                        "\uD83D\uDCE6 *Blokdagi miqdori:* _" + String.format("%,.2f", product.getAllAmount()) + " " + product.getWeightType() + "_\n" +
                                                        "\t\uD83D\uDCB0 *Blokdagi narxi:* _" + String.format("%,.2f", product.getAllPrice()) + "so'm _ \n\n" +
                                                        "\uD83D\uDCCB *Quyida buyurtmangiz " + sellType + "dagi sonini tanlang:*");
                                                codeMessage.setType(CodeMessageType.PHOTO);
                                                codeMessage.setSendPhoto(sendPhoto);
                                                sendPhoto.setReplyMarkup(resButton("/universal/" + command + "/" + commandList[3] + "/" + sellType + "/", commandList[4]));
                                                return codeMessage;
                                            }
                                        }
                                    }
                                } else {
                                    sendMessage.setText("Bunday mahsulot topilmadi!");
                                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                            InlineButtonUtil.collection(
                                                    InlineButtonUtil.row(InlineButtonUtil.button("✅️️️ Menu ✅", "menu"))
                                            )));
                                }
                                codeMessage.setType(CodeMessageType.MESSAGE);
                                codeMessage.setSendMessage(sendMessage);
                            }
                        } else if (commandList[3].equals("confirmed")) {
                            OrderItem order = this.orderRepository.orderMap.get(Integer.parseInt(commandList[4]));
                            if (commandList.length >= 6) {
                                if (commandList[5].equals("yes")) {
                                    if (order.getOrderItemType().equals(OrderItemType.ACCEPTED_TIME)) {
                                        order.setOrderItemType(OrderItemType.FINISHED);
                                        order.setAcceptedDate(new Date());
                                        sendMessage.setParseMode("Markdown");
                                        this.orderRepository.addOrder(order.getId(), order);
                                        for (ProductItem productItem : order.getProductItems()) {
                                            ProductItem productItem1 = this.productRepository.productMap.get(productItem.getKeyName());
                                            if (productItem.getCount() > productItem1.getCount()) {
                                                sendMessage.setText("*Mahsulot yetmaydi!*\n" +
                                                        "*Omborda bor mahsulot: * " + productItem1.getName() + "(" + productItem1.getKeyName() + ") - _" +
                                                        String.format("%,.0f", productItem1.getCount()) + " ta_ qolgan!\n" +
                                                        "*Mijoz so'rayotgan mahsulot soni* - _" + String.format("%,.0f", productItem.getCount()) + " ta_\n" +
                                                        "*Shuning uchun buyurtma bekor qilindi!*");
                                                sendMessage.setChatId((long) 708154659);
                                                execute(sendMessage);

                                                sendMessage.setChatId(chatId);
                                                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                                replyKeyboardMarkup.setSelective(true);
                                                replyKeyboardMarkup.setResizeKeyboard(true);
                                                replyKeyboardMarkup.setOneTimeKeyboard(true);

                                                // new list
                                                List<KeyboardRow> keyboard = new ArrayList<>();

                                                // first keyboard line
                                                KeyboardRow keyboardFirstRow = new KeyboardRow();
                                                KeyboardButton keyboardButton = new KeyboardButton();
                                                keyboardButton.setText("Menu");
                                                keyboardFirstRow.add(keyboardButton);

                                                // add array to list
                                                keyboard.add(keyboardFirstRow);

                                                // add list to our keyboard
                                                replyKeyboardMarkup.setKeyboard(keyboard);
                                                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                                                sendMessage.setText("Xatolik tufayli buyurtmangiz bekor qilindi! Iltimos, qaytadan urinib ko'ring! !");

                                                order.setOrderItemType(OrderItemType.CANCELLED);
                                                this.orderRepository.addOrder(order.getId(), order);
                                                codeMessage.setType(CodeMessageType.MESSAGE);
                                                codeMessage.setSendMessage(sendMessage);
                                                return codeMessage;
                                            }
                                            productItem1.setCount(productItem1.getCount() - productItem.getCount());
                                            this.productRepository.productMap.put(productItem.getKeyName(), productItem1);
                                        }
                                        sendMessage.setText("Buyurtma qabul qilindi!");
                                        sendDocument = new SendDocument();
                                        sendDocument.setChatId(chatId);
                                        File file = this.document.orderXlsDocument(order);
                                        codeMessage.setSendDocument(sendDocument);
                                        sendDocument.setDocument(file);
                                        codeMessage.setType(CodeMessageType.MESSAGE_FILE);
                                    } else {
                                        sendMessage.setText("Buyurtma ustida amaliyot allaqchon bajarib bo'lingan!");
                                        codeMessage.setSendMessage(sendMessage);
                                        codeMessage.setType(CodeMessageType.MESSAGE);
                                        return codeMessage;
                                    }
                                } else {
                                    if (order.getOrderItemType().equals(OrderItemType.ACCEPTED_TIME)) {
                                        order.setOrderItemType(OrderItemType.CANCELLED);
                                        this.orderRepository.addOrder(order.getId(), order);
                                        sendMessage.setText("Buyurtma bekor qilindi!");
                                        codeMessage.setSendMessage(sendMessage);
                                        codeMessage.setType(CodeMessageType.MESSAGE);
                                        return codeMessage;
                                    } else {
                                        sendMessage.setText("Buyurtma ustida amaliyot allaqchon bajarib bo'lingan!");
                                        codeMessage.setSendMessage(sendMessage);
                                        codeMessage.setType(CodeMessageType.MESSAGE);
                                        return codeMessage;
                                    }
                                }
                                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(InlineButtonUtil.button("✅️️️ Menu ✅", "/adminPanel"))
                                        )));

                                codeMessage.setSendMessage(sendMessage);
                                return codeMessage;
                            }
                            if (order.getOrderItemType().equals(OrderItemType.SELECTED)) {
                                order.setDate(new Date());
                                order.setUsername(message.getChat().getUserName() != null ? message.getChat().getUserName() : "--");
                                order.setUserName(message.getChat().getFirstName() == null ? "----" : message.getChat().getFirstName());
                                String orderUser = ("\t\uD83D\uDC64 *Buyurtmachi username:* [Mijoz](tg://user?id=" + chatId + ")" +
                                        "\n*Buyurtmachi ismi:*  " +
                                        message.getChat().getFirstName() + " " +
                                        message.getChat().getLastName());

                                StringBuilder stringBuilder = new StringBuilder();
                                int countNum = 1;
                                double allPrice = 0;
                                sendMessage.setParseMode("Markdown");
                                for (ProductItem productItem : order.getProductItems()) {
                                    int blokCount = 0;
                                    double count = 0.00;

                                    if (productItem.getCount() > (productItem.getAllAmount() / productItem.getAmount())) {
                                        blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
                                        count = productItem.getCount() % (productItem.getAllAmount() / productItem.getAmount());
                                    } else if (productItem.getCount() == (productItem.getAllAmount() / productItem.getAmount())) {
                                        blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
                                    } else if (productItem.getCount() < (productItem.getAllAmount() / productItem.getAmount())) {
                                        count = productItem.getCount();
                                    }
                                    stringBuilder.append("\n\n");
                                    stringBuilder.append("*" + countNum + ". Mahsulot nomi:* _" + productItem.getName() + "("
                                            + productItem.getKeyName() + ")_");
                                    stringBuilder.append("\n");
                                    stringBuilder.append("\uD83D\uDCB8 *Narxi: " + String.format("%,.2f", productItem.getTotalPrice()) + " so'm* \n");
                                    stringBuilder.append((count > 0 ? String.format("%,.0f", count) + " (дона) x " + String.format("%,.2f", productItem.getPrice()) + " (so'm) = *" +
                                            String.format("%,.2f", (count * productItem.getPrice())) + "  so'm*\n" : ""));
                                    stringBuilder.append((blokCount > 0 ? blokCount + " (кор.) x " + String.format("%,.2f", productItem.getAllPrice()) + " (so'm) = *" +
                                            String.format("%,.2f", (blokCount * productItem.getAllPrice())) + "  so'm*\n" : ""));
                                    stringBuilder.append("-----------------------------------------");
                                    stringBuilder.append("\n\n");
                                    allPrice += (productItem.getTotalPrice());
                                    countNum++;

                                }
                                order.setOrderItemType(OrderItemType.ACCEPTED_TIME);
                                order.setAllPrice(allPrice);
                                this.orderRepository.addOrder(order.getId(), order);
                                sendMessage.setText(" * ---- \uD83D\uDCC3 Buyurtma - ️" + order.getOrderNumber() + " \uD83D\uDCC3 ---- *\n\n" +
                                        "*Buyurtmachi username:* [Mijoz](tg://user?id=" + chatId + ")\n" +
                                        "\n*Buyurtmachi ismi:*  " +
                                        message.getChat().getFirstName() + " \n" +
                                        "\n\t\uD83D\uDCE6 *Buyurtma*: " + stringBuilder + "\n\n" +
                                        "\n\t\uD83D\uDCF2 *Telefon:* " + order.getPhoneNumber() +
                                        "\n\t\uD83D\uDD50 *Buyurtma berilgan vaqti:* _" + simpleDateFormat.format(order.getDate()) + "_\n" +
                                        "\n\uD83D\uDCB8 *Buyurtma narxi:* _" + String.format("%,.2f", order.getAllPrice()) + " so'm _\n" +
                                        "\n\uD83C\uDD97* Buyurtmani tasdiqlaysizmi ⁉️*\n");
                                SendLocation sendLocation = new SendLocation();
                                sendLocation.setLatitude(Float.parseFloat(order.getLat()));
                                sendLocation.setLongitude(Float.parseFloat(order.getLan()));
                                sendLocation.setChatId((long) 708154659);
                                sendLocation.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(InlineButtonUtil.button("✅️️️ Ha ✅", "/universal/order/confirmed/" + order.getId() + "/yes/" + chatId)),
                                                InlineButtonUtil.row(InlineButtonUtil.button("❌ Yo'q ❌ ️", "/universal/order/confirmed/" + order.getId() + "/no/" + chatId))
                                        )));
                                sendMessage.setChatId((long) 708154659);
                                execute(sendMessage);
                                execute(sendLocation);

                                sendMessage.setChatId(chatId);
                                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                replyKeyboardMarkup.setSelective(true);
                                replyKeyboardMarkup.setResizeKeyboard(true);
                                replyKeyboardMarkup.setOneTimeKeyboard(true);

                                // new list
                                List<KeyboardRow> keyboard = new ArrayList<>();

                                // first keyboard line
                                KeyboardRow keyboardFirstRow = new KeyboardRow();
                                KeyboardButton keyboardButton = new KeyboardButton();
                                keyboardButton.setText("Menu");
                                keyboardFirstRow.add(keyboardButton);

                                // add array to list
                                keyboard.add(keyboardFirstRow);

                                // add list to our keyboard
                                replyKeyboardMarkup.setKeyboard(keyboard);
                                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                                sendMessage.setText("Buyurtmangiz qabul qilindi! Tez orada operator siz bilan bog'lanadi !");
                                codeMessage.setType(CodeMessageType.MESSAGE);
                                codeMessage.setSendMessage(sendMessage);
                                return codeMessage;
                            } else {
                                String urlString = "" +
                                        "https://api.telegram.org/bot1117431038:AAFHCZVhC47wFLRPbI9ca_paHygly_5h11M/sendMessage?chat_id=" +
                                        chatId + "&text=Buyurtmangiz allaqachon ko'rib chiqilmoqda! Tez orada operator siz bilan bog'lanadi !";
                                try {
                                    URL url = new URL(urlString);
                                    URLConnection conn = url.openConnection();
                                    InputStream is = new BufferedInputStream(conn.getInputStream());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else if
                    (commandList.length >= 4) {
                        if (commandList[3].equals("getPhoneNumber")) {
                            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            replyKeyboardMarkup.setSelective(true);
                            replyKeyboardMarkup.setResizeKeyboard(true);
                            replyKeyboardMarkup.setOneTimeKeyboard(true);

                            // new list
                            List<KeyboardRow> keyboard = new ArrayList<>();

                            // first keyboard line
                            KeyboardRow keyboardFirstRow = new KeyboardRow();
                            KeyboardButton keyboardButton = new KeyboardButton();
                            keyboardButton.setText("Telefon raqamni jo'natish");
                            keyboardButton.setRequestContact(true);
                            keyboardFirstRow.add(keyboardButton);

                            // add array to list
                            keyboard.add(keyboardFirstRow);

                            // add list to our keyboard
                            replyKeyboardMarkup.setKeyboard(keyboard);

                            ProductItem productTemp = new ProductItem();
                            productTemp.setId(this.productRepository.productMap.size() + 1);
                            productTemp.setUserId(chatId);
                            productTemp.setProductItemType(ProductItemType.GET_PHONE);
                            this.productTempMap.put(chatId, productTemp);

                            sendMessage.setText("Buyurtma qilish uchun telefon raqamingizni jo'nating:");
                            codeMessage.setType(CodeMessageType.MESSAGE);
                            codeMessage.setSendMessage(sendMessage);
                        }
                        return codeMessage;
                    }
                    break;
                case "basket":
                    OrderItem orderList = this.orderRepository.getUserOrderList(chatId);
                    StringBuilder stringBuilder = new StringBuilder("");
                    sendMessage.setParseMode("HTML");
                    if (orderList != null) {
                        if (commandList.length >= 4) {
                            if (commandList[3].equals("clear")) {
                                this.orderRepository.deleteOrder(orderList.getOrderNumber());
                                sendMessage.setText("*Savat tozalandi!*\n" +
                                        "__Davom etamizmi?__ \t\uD83D\uDE09 ");
                                sendMessage.setChatId(chatId);
                                sendMessage.setParseMode("Markdown");

                                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("\uD83E\uDD56 Pechenyelar \uD83E\uDD56", "/universal/user/sweet"),
                                                        InlineButtonUtil.button("\uD83E\uDD42 Ichimliklar \uD83E\uDD42", "/universal/user/drink")

                                                ),
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button("\t\uD83D\uDC76 Tagliklar \t\uD83D\uDC76", "/universal/user/diaper"),
                                                        InlineButtonUtil.button("\uD83D\uDDD1 ️Savat \uD83D\uDDD1️", "/universal/basket")
                                                )
                                        )));


                                codeMessage.setType(CodeMessageType.MESSAGE);
                                codeMessage.setSendMessage(sendMessage);
                                return codeMessage;
                            } else if (commandList[3].equals("product") || commandList[4].equals("clear")) {
                                sendMessage.setParseMode("HTML");
                                if (commandList.length == 6) {
                                    List<ProductItem> productItems = orderList.getProductItems();
                                    for (int i = 0; i < productItems.size(); i++) {
                                        if (productItems.get(i).getKeyName().equals(commandList[5])) {
                                            productItems.remove(i);
                                            break;
                                        }
                                    }
                                    if (orderList.getProductItems().size() > 0) {
                                        orderList.setProductItems(productItems);
                                        this.orderRepository.addOrder(orderList.getOrderNumber(), orderList);
                                    } else {
                                        this.orderRepository.deleteOrder(orderList.getOrderNumber());
                                    }
                                    this.orderProductBasket(stringBuilder, orderList, sendMessage);
                                    sendMessage.setChatId(chatId);
                                    codeMessage.setSendMessage(sendMessage);
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    return codeMessage;

                                } else {
                                    int countNum = 1;
                                    double allPrice = 0;
                                    stringBuilder.append("<b> ---- \t\uD83D\uDCE4 Buyurtmalar ro'yhati \t\uD83D\uDCE4 ---- </b>");
                                    for (ProductItem dto : orderList.getProductItems()) {
                                        int blokCount = 0;
                                        double count = 0.00;

                                        if (dto.getCount() > (dto.getAllAmount() / dto.getAmount())) {
                                            blokCount = (int) (dto.getCount() / (dto.getAllAmount() / dto.getAmount()));
                                            count = dto.getCount() % (dto.getAllAmount() / dto.getAmount());
                                        } else if (dto.getCount() == (dto.getAllAmount() / dto.getAmount())) {
                                            blokCount = (int) (dto.getCount() / (dto.getAllAmount() / dto.getAmount()));
                                        } else if (dto.getCount() < (dto.getAllAmount() / dto.getAmount())) {
                                            count = dto.getCount();
                                        }
                                        stringBuilder.append("\n\n");
                                        stringBuilder.append("<b>" + countNum + ". Mahsulot nomi: </b><i>" + dto.getName() + "</i>");
                                        stringBuilder.append("\n");
                                        stringBuilder.append("\uD83D\uDCB8 <b>Narxi: " + String.format("%,.2f", dto.getTotalPrice()) + " so'm<b><i> \n");
                                        stringBuilder.append((count > 0 ? String.format("%,.0f", count) + " (дона) x " + String.format("%,.2f", dto.getPrice()) + " (so'm) </i>= <b>" +
                                                String.format("%,.2f", (count * dto.getPrice())) + "  so'm</b>\n<i>" : ""));
                                        stringBuilder.append((blokCount > 0 ? blokCount + " (кор.) x " + String.format("%,.2f", dto.getAllPrice()) + " (so'm)</i> = <b>" +
                                                String.format("%,.2f", (blokCount * dto.getAllPrice())) + "  so'm</b>\n" : ""));
                                        stringBuilder.append("-----------------------------------------");
                                        stringBuilder.append("\n\n");
                                        allPrice += dto.getTotalPrice();
                                        countNum++;
                                    }

                                    if (allPrice >= 100000.00) {
                                        stringBuilder.append("-----------------------------------------\n" +
                                                "\n<b>\t\uD83D\uDCB0 Umumiy narxi: <i>" + String.format("%,.2f", allPrice) + "</i> so'm </b>\n\n");
                                    } else {
                                        stringBuilder.append("-----------------------------------------\n" +
                                                "\n<b>\t\uD83D\uDCB0 Umumiy narxi: <del>" + String.format("%,.2f", allPrice) + "</del> so'm </b>\n\n");
                                        stringBuilder.append("<b>\t\uD83D\uDCB0 Umumiy narxi: 100,000 so'mda kam bo'lamsiligi kerak!</b>");
                                    }
                                    sendMessage.setText(stringBuilder.toString());

                                    List<List<InlineKeyboardButton>> collection = InlineButtonUtil.collection();
                                    List<InlineKeyboardButton> row = InlineButtonUtil.row();
                                    for (ProductItem productItem : orderList.getProductItems()) {
                                        row.add(InlineButtonUtil
                                                .button(
                                                        (" ❌ " + productItem.getName()),
                                                        "/universal/basket/product/clear/" + productItem.getKeyName()));
                                        collection.add(row);
                                        row = new ArrayList<>();

                                    }
                                    collection.add(InlineButtonUtil.row(InlineButtonUtil.button("⬅️ Menyuga qaytish", "menu")));
                                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(collection));
                                    codeMessage.setSendMessage(sendMessage);
                                    codeMessage.setType(CodeMessageType.MESSAGE);
                                    return codeMessage;
                                }
                            }
                        } else {
                            this.orderProductBasket(stringBuilder, orderList, sendMessage);
                        }
                    } else {
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setText("Siz xali mahsulot tanlamadigiz !");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button(" ⬅️ Menyuga qaytish", "menu")
                                        )
                                )));
                    }

                    sendMessage.setChatId(chatId);
                    codeMessage.setSendMessage(sendMessage);
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    break;
                case "page":
                    sendMessage.setReplyMarkup(this.getProductPageKeyBoard(commandList[4], commandList[3],
                            Integer.parseInt(commandList[5])));
                    sendMessage.setText("Mahsulotlar ro'yhati");
                    codeMessage.setSendMessage(sendMessage);
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    return codeMessage;
                case "sendMes":
                    if (commandList.length == 3) {
                        sendMessage.setText("Xabar yuborish turini tanlang!");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                InlineButtonUtil.row(InlineButtonUtil.button("\uD83D\uDCE4 Xabar yuborish \uD83D\uDCE4", "/universal/sendMes/message"),
                                        InlineButtonUtil.button("\uD83D\uDCF8️️️️ Rasmli xabar yuborish \uD83D\uDCE4️", "/universal/sendMes/mesPhoto")),
                                InlineButtonUtil.row(InlineButtonUtil.button("\uD83D\uDCF8️️️️ Rasm yuborish \uD83D\uDCF8️️", "/universal/sendMes/photo"),
                                        InlineButtonUtil.button("\t\uD83D\uDCF9️️️ Video xabar yuborish \t\uD83D\uDCF9️", "/universal/sendMes/video")),
                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menu ❇️", "/adminPanel"))

                        )));
                        codeMessage.setSendMessage(sendMessage);
                        codeMessage.setType(CodeMessageType.MESSAGE);
                        return codeMessage;
                    } else if (commandList.length == 4) {
                        switch (commandList[3]) {
                            case "message":
                                sendMessage.setText("Xabarni yuboring:");
                                ProductItem productItem = new ProductItem();
                                productItem.setId(this.productRepository.productMap.size() + 1);
                                productItem.setUserId(chatId);
                                productItem.setProductItemType(ProductItemType.MESSAGE);
                                this.productTempMap.put(chatId, productItem);

                                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/senMes")
                                                ),
                                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                        )));
                                break;
                            case "mesPhoto":
                                sendMessage.setText("Rasmni yuboring:");
                                productItem = new ProductItem();
                                productItem.setId(this.productRepository.productMap.size() + 1);
                                productItem.setUserId(chatId);
                                productItem.setProductItemType(ProductItemType.MESSAGE_PHOTO_MES);
                                this.productTempMap.put(chatId, productItem);
                                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/sendMes")
                                                ),
                                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                        )));
                                break;
                            case "photo":
                                sendMessage.setText("Rasmni yuboring:");
                                productItem = new ProductItem();
                                productItem.setId(this.productRepository.productMap.size() + 1);
                                productItem.setUserId(chatId);
                                productItem.setProductItemType(ProductItemType.MES_PHOTO);
                                this.productTempMap.put(chatId, productItem);
                                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/sendMes")
                                                ),
                                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                        )));
                                break;
                            case "video":
                                sendMessage.setText("Videoni yuboring:");
                                productItem = new ProductItem();
                                productItem.setId(this.productRepository.productMap.size() + 1);
                                productItem.setUserId(chatId);
                                productItem.setProductItemType(ProductItemType.MES_VIDEO);
                                this.productTempMap.put(chatId, productItem);
                                sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                        InlineButtonUtil.collection(
                                                InlineButtonUtil.row(
                                                        InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/sendMes")
                                                ),
                                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                        )));
                                break;
                        }
                        codeMessage.setSendMessage(sendMessage);
                        codeMessage.setType(CodeMessageType.MESSAGE);
                        return codeMessage;

                    } else if (commandList.length == 5) {
                        ProductItem sendMesPr = this.productTempMap.get(chatId);
                        switch (commandList[3]) {
                            case "mesPhoto":
                                if (sendMesPr != null) {
                                    for (UserItem userItem : this.userRepository.userMap.values()) {
                                        sendPhoto.enableNotification();
                                        sendPhoto.setPhoto(sendMesPr.getPhotoId());
                                        sendPhoto.setCaption(sendMesPr.getMessage());
                                        sendPhoto.setChatId(userItem.getId());
                                        sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(
                                                InlineButtonUtil.collection(
                                                        InlineButtonUtil.row(InlineButtonUtil.button("\uD83D\uDE9A Buyurtma qilish \uD83D\uDE9A", "menu")),
                                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Biz haqimizda ❇️", "/universa/about"))
                                                )));
                                        execute(sendPhoto);
                                    }
                                    sendMessage.setText("Xabarlar yuborildi!");
                                } else {
                                    sendMessage.setText("Xatolik qaytadan urinib ko'ring!");
                                }
                                break;
                            case "message":
                                if (sendMesPr != null) {
                                    for (UserItem userItem : this.userRepository.userMap.values()) {
                                        sendMessage.enableWebPagePreview();
                                        sendMessage.enableNotification();
                                        sendMessage.setText(sendMesPr.getMessage());
                                        sendMessage.setChatId(userItem.getId());
                                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                                InlineButtonUtil.collection(
                                                        InlineButtonUtil.row(InlineButtonUtil.button("\uD83D\uDE9A Buyurtma qilish \uD83D\uDE9A", "menu")),
                                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Biz haqimizda ❇️", "/universa/about"))
                                                )));
                                        execute(sendMessage);
                                    }
                                    sendMessage.setText("Xabarlar yuborildi!");
                                } else {
                                    sendMessage.setText("Xatolik qaytadan urinib ko'ring!");
                                }
                                break;
                            case "photo":
                                if (sendMesPr != null) {
                                    for (UserItem userItem : this.userRepository.userMap.values()) {
                                        sendPhoto.enableNotification();
                                        sendPhoto.setPhoto(sendMesPr.getPhotoId());
                                        sendPhoto.setChatId(userItem.getId());
                                        sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(
                                                InlineButtonUtil.collection(
                                                        InlineButtonUtil.row(InlineButtonUtil.button("\uD83D\uDE9A Buyurtma qilish \uD83D\uDE9A", "menu")),
                                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Biz haqimizda ❇️", "/universa/about"))
                                                )));
                                        execute(sendPhoto);
                                    }
                                    sendMessage.setText("Xabarlar yuborildi!");
                                } else {
                                    sendMessage.setText("Xatolik qaytadan urinib ko'ring!");
                                }
                                break;
                            case "video":
                                if (sendMesPr != null) {
                                    for (UserItem userItem : this.userRepository.userMap.values()) {
                                        sendVideo.enableNotification();
                                        sendVideo.setVideo(sendMesPr.getPhotoId());
                                        sendVideo.setChatId(userItem.getId());
                                        sendVideo.setReplyMarkup(InlineButtonUtil.keyboard(
                                                InlineButtonUtil.collection(
                                                        InlineButtonUtil.row(InlineButtonUtil.button("\uD83D\uDE9A Buyurtma qilish \uD83D\uDE9A", "menu")),
                                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Biz haqimizda ❇️", "/universa/about"))
                                                )));
                                        execute(sendVideo);
                                    }
                                    sendMessage.setText("Xabarlar yuborildi!");
                                } else {
                                    sendMessage.setText("Xatolik qaytadan urinib ko'ring!");
                                }
                                break;
                        }
                        sendMessage.setChatId(chatId);
                        this.productTempMap.remove(chatId);
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                InlineButtonUtil.row(InlineButtonUtil.button("\uD83D\uDCE4 Xabar yuborish \uD83D\uDCE4️", "/universal/sendMes"),
                                        InlineButtonUtil.button("❇️️️ Menu ❇️", "/adminPanel"))
                        )));
                        codeMessage.setType(CodeMessageType.MESSAGE);
                        codeMessage.setSendMessage(sendMessage);
                        return codeMessage;

                    }
                    break;
                case "stat":
                    int userCount = this.userRepository.userMap.size();
                    Map<Long, String> hashList = new HashMap<>();
                    if (this.orderRepository.orderMap.size() > 0) {
                        for (OrderItem orderItem : this.orderRepository.orderMap.values()) {
                            if (orderItem.getOrderItemType().equals(OrderItemType.FINISHED)) {
                                hashList.put(orderItem.getUserId(), " ");
                            }
                        }
                    }
                    int clientUser = hashList.size();
                    sendMessage.setParseMode("Markdown");
                    sendMessage.setText("* ---- Statistika ---- *\n\n" +
                            "*Umumiy foydalanuvchilar: *" + userCount +
                            "*\nFaol foydalanuvchilar: *" + clientUser);
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
            }
            // Qo'lda mahsulot qo'shish
            return codeMessage;
        }

        if (this.productTempMap.containsKey(chatId)) {
            ProductItem productItem = this.productTempMap.get(chatId);
            sendMessage.setParseMode("Markdown");
            switch (productItem.getProductItemType()) {
                case ABOUT_EDIT:
                    String s = "";
                    if (aboutList.size() > 0) {
                        s = aboutList.get(0);
                        aboutList.clear();
                        aboutList.add(text);
                    } else {
                        aboutList.add(text);
                    }
                    sendMessage.setText("Tahrirlandi!");
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("⬅️️ Orqaga️️", "/universal/about/admin"),
                                    InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")
                            )
                    )));
                    this.productTempMap.remove(chatId);
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;

                case REPORT_PRODUCT:
                    sendMessage.setText("Hisobot!");
                    String dateFormat = "year";
                    if (text.length() == 7) {
                        dateFormat = "month";
                    } else if (text.length() >= 7) {
                        dateFormat = "day";
                    }
                    List<ProductItem> productItems1 = this.makeOrderByProductReport(text, dateFormat);
                    File file = this.document.reportOrderByProduct(productItems1);
                    sendDocument.setDocument(file);
                    sendDocument.setChatId(chatId);
                    codeMessage.setType(CodeMessageType.MESSAGE_FILE);
                    codeMessage.setSendDocument(sendDocument);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case REPORT_USER:
                    sendMessage.setText("Hisobot!");
                    dateFormat = "year";
                    if (text.length() == 7) {
                        dateFormat = "month";
                    } else if (text.length() >= 7) {
                        dateFormat = "day";
                    }
                    List<OrderItem> orderItems = new ArrayList<>();
                    for (OrderItem orderItem : this.orderRepository.orderMap.values()) {
                        if (orderItem.getOrderItemType().equals(OrderItemType.FINISHED)) {
                            orderItems.add(orderItem);
                        }
                    }
                    File file1 = this.document.reportOrderByUser(orderItems, dateFormat, text);
                    sendDocument.setDocument(file1);
                    sendDocument.setChatId(chatId);
                    codeMessage.setType(CodeMessageType.MESSAGE_FILE);
                    codeMessage.setSendDocument(sendDocument);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;

                case UPDATE_CATEGORY:
                    List<OrderItem> orderList = (List<OrderItem>) this.orderRepository.orderMap.values();
                    sendMessage.setText("Hisobot!");
                    dateFormat = "year";
                    if (text.length() == 7) {
                        dateFormat = "month";
                    } else if (text.length() >= 7) {
                        dateFormat = "day";
                    }
                    sendDocument.setChatId(chatId);
                    codeMessage.setType(CodeMessageType.MESSAGE_FILE);
                    codeMessage.setSendDocument(sendDocument);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case ADD_FILE:
                    List<ProductItem> productItems = this.document.productAddByFile(text);
                    for (ProductItem productItem1 : productItems) {
                        this.productRepository.productMap.put(productItem1.getKeyName(), productItem1);
                    }
                    sendMessage.setText("Mahsulotlar muvaffaqiyatli qo'shildi!");
                    codeMessage.setSendMessage(sendMessage);
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    return codeMessage;
                case NAME:
                    productItem.setName(text);
                    sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                            "🔠 Nomi: *" + productItem.getName() + "*\n\n\n" +
                            "*Mahsulot kodini jo'nating:*");
                    productItem.setProductItemType(ProductItemType.KEY_NAME);
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    break;
                case KEY_NAME:
                    if (this.productRepository.getProduct(text) == null) {
                        productItem.setKeyName(text);
                        sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n\n\n" +
                                "🗂️ Mahsulot bo'limini kiriting yoki tanlang:\n" +
                                "*(sweet(pechenye), drink(ichimlik), diaper(taglik))*\n" +
                                "(Misol: *sweet*)");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                InlineButtonUtil.row(
                                        InlineButtonUtil.button(" Pechenye ", "/universal/product/add/category/sweet", ":cake:"),
                                        InlineButtonUtil.button(" Ichimliklar ", "/universal/product/add/category/drink", ":cocktail:")
                                ),
                                InlineButtonUtil.row(InlineButtonUtil.button("️️️Tagliklar ", "/universal/product/add/category/diaper", ":baby:")),
                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                        )));
                        productItem.setProductItemType(ProductItemType.CATEGORY_NAME);
                    } else {
                        sendMessage.setText("❌ Bunday mahsulot bor! ❌");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    }
                    break;
                case CATEGORY_NAME:
                    try {
                        if (text.equals("sweet") || text.equals("diaper") || text.equals("drink")) {
                            productItem.setCategoryName(text);
                            sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                    "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                    "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                    "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n\n\n" +
                                    "*Mahsulot narxini jo'nating( __so'mda__):*\n" +
                                    "(Misol: *3000*)");
                            productItem.setProductItemType(ProductItemType.PRICE);
                        } else {
                            sendMessage.setText("Notog'ri ma'lumot kiritdingiz!");
                        }
                    } catch (NumberFormatException e) {
                        sendMessage.setText("❌ Notog'ri ma'lumot kiritdingiz! ❌ ");
                    }
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    break;
                case PRICE:
                    try {
                        productItem.setPrice(Double.parseDouble(text));
                        sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                "🗂️ Bo'lim: *" + productItem.getCategoryName() + "  so'm*\n" +
                                "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n\n\n" +
                                "Eng kam sotiladigan karobkadagi narxini jo'nating( *so'mda*):\n" +
                                "Misol: *300*");
                        productItem.setProductItemType(ProductItemType.ALL_PRICE);
                    } catch (NumberFormatException e) {
                        sendMessage.setText("❌ Faqat son kiriting! ❌");
                    }
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    break;
                case ALL_PRICE:
                    try {
                        productItem.setAllPrice(Double.parseDouble(text));
                        sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                                "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n" +
                                "💵 Eng kam sotiladigan karobkadagi narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + " so'm*\n\n\n" +
                                "Bir mahsulot miqdorini jo'nating( *gr da, L da, kg da...*):\n" +
                                "Misol: *3000*");
                        productItem.setProductItemType(ProductItemType.AMOUNT);
                    } catch (NumberFormatException e) {
                        sendMessage.setText("❌ Faqat son kiriting! ❌");
                    }
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    break;
                case MESSAGE:
                    productItem.setMessage(text);
                    sendMessage.setText(text + "\n\n\nXabarni tasdiqlaysizmi?");
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("✅ Ha ✅", "/universal/sendMes/message/yes")
                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("❌ Yo'q ❌", "/universal/sendMes/message/no")
                            )
                    )));
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case MESSAGE_PHOTO_MES:
                    productItem.setPhotoId(text);
                    sendMessage.setText("Xabarni yuboring:");
                    productItem.setProductItemType(ProductItemType.MESSAGE_PHOTO_PHO);
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case MESSAGE_PHOTO_PHO:
                    productItem.setMessage(text);
                    sendMessage.setText("Xabarni tasdiqlaysizmi?");
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("✅ Ha ✅", "/universal/sendMes/mesPhoto/yes")
                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("❌ Yo'q ❌", "/universal/sendMes/mesPhoto/no")
                            )
                    )));
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case MES_PHOTO:
                    productItem.setPhotoId(text);
                    sendMessage.setText("Xabarni tasdiqlaysizmi?");
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("✅ Ha ✅", "/universal/sendMes/photo/yes")
                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("❌ Yo'q ❌", "/universal/sendMes/photo/no")
                            )
                    )));
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case MES_VIDEO:
                    productItem.setPhotoId(text);
                    sendMessage.setText("Xabarni tasdiqlaysizmi?");
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("✅ Ha ✅", "/universal/sendMes/video/yes")
                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("❌ Yo'q ❌", "/universal/sendMes/video/no")
                            )
                    )));
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case AMOUNT:
                    try {
                        productItem.setAmount(Double.parseDouble(text));
                        sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                                "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + "  so'm*\n" +
                                "💵 Eng kam sotiladigan karobkadagi narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + "  so'm*\n" +
                                "📦 Miqdori: *" + String.format("%,.2f", productItem.getAmount()) + "*\n\n\n\"" +
                                "📦 Miqdorining turini  jo'nating:\n" +
                                "(*gr, l, dona*)\n" +
                                "(Misol: *gr*)");
                        productItem.setProductItemType(ProductItemType.WEIGHT_TYPE);
                    } catch (NumberFormatException e) {
                        sendMessage.setText("❌ Faqat son kiriting! ❌");
                    }
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    break;
                case WEIGHT_TYPE:
                    productItem.setWeightType(text);
                    sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                            "🔠 Nomi: *" + productItem.getName() + "*\n" +
                            "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                            "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                            "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n" +
                            "💵 Eng kam sotiladigan karobkadagi narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + " so'm*\n" +
                            "📦 Miqdori: *" + String.format("%,.2f", productItem.getAmount()) + " " + productItem.getWeightType() + "*\n\n\n" +
                            "Mahsulotning umumiy miqdori(*karobka*)  jo'nating:\n" +
                            "(Misol: *3000*)");
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    productItem.setProductItemType(ProductItemType.ALL_AMOUNT);
                    break;
                case ALL_AMOUNT:
                    try {
                        productItem.setAllAmount(Double.parseDouble(text));
                        sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                                "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n" +
                                "💵 Eng kam sotiladigan karobkadagi narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + " so'm*\n" +
                                "📦 Miqdori: *" + String.format("%,.2f", productItem.getAmount()) + " " + productItem.getWeightType() + "*\n" +
                                "📦 Eng kam sotiladigan karobkadagi miqdori: *" + String.format("%,.2f", productItem.getAllAmount()) + " " + productItem.getWeightType() + "*\n\n\n" +
                                "Mahsulotning sotish turni tanlang:\n");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                InlineButtonUtil.row(
                                        InlineButtonUtil.button("📦 Donalab 📦", "/universal/product/sellType/dona/" + productItem.getKeyName()),
                                        InlineButtonUtil.button("📦 Karobkaga 📦️", "/universal/product/sellType/blok/" + productItem.getKeyName())
                                ),
                                InlineButtonUtil.row(InlineButtonUtil.button("️️️📦 Ikkalasi ham 📦", "/universal/product/sellType/all/" + productItem.getKeyName())),
                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                        )));
                        productItem.setProductItemType(ProductItemType.SELL_TYPE);
                    } catch (NumberFormatException e) {
                        sendMessage.setText("❌ Faqat son kiriting! ❌");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    }
                    break;
                case SELL_TYPE:
                    sendMessage.setText("Xatolik! Yuqoridagilardan birini tanlang!");
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case COUNT:
                    try {
                        productItem.setCount(Integer.parseInt(text) * (productItem.getAllAmount() / productItem.getAmount()));
                        String sellType = this.getProductSellType(productItem);
                        sendMessage.setText("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                                "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n" +
                                "\t\uD83D\uDD22 Soni: *" +
                                String.format("%,.0f", productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount())) + " ta (karobkada), " +
                                String.format("%,.0f", productItem.getCount()) + " ta (donada) "
                                + "*\n" +
                                "💵 Eng kam sotiladigan karobkadagi narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + " so'm*\n" +
                                "📦 Miqdori: *" + String.format("%,.2f", productItem.getAmount()) + " "
                                + productItem.getWeightType() + "*\n" +
                                "📦 Eng kam sotiladigan karobkadagi miqdori: *" +
                                String.format("%,.2f", productItem.getAllAmount()) + " " + productItem.getWeightType() + "*\n" +
                                "🗂️ Sotish turi: *" + sellType + "*\n\n\n" +
                                "Masulot rasmini jo'nating!");
                        productItem.setProductItemType(ProductItemType.PHOTO);
                    } catch (NumberFormatException e) {
                        sendMessage.setText("\n Faqat son kiriting\n");
                    }
                    sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                    break;
                case UPDATE_CONTENT:
                    productItem.setCount(Integer.parseInt(text) * (productItem.getAllAmount() / productItem.getAmount()));
                    String sellType = this.getProductSellType(productItem);
                    sendMessage.setText("* ---- 📝 Tahrirlanayotgan mahsulot 	📝 ---- *\n" +
                            "🔠 Nomi: *" + productItem.getName() + "*\n" +
                            "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                            "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                            "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n" +
                            "\t\uD83D\uDD22 Soni: *" +
                            String.format("%,.0f", productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount())) + " ta (karobkada), " +
                            String.format("%,.0f", productItem.getCount()) + " ta (donada) "
                            + "*\n" +
                            "💵 Eng kam sotiladigan karobkadagi narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + " so'm*\n" +
                            "📦 Miqdori: *" + String.format("%,.2f", productItem.getAmount()) + " "
                            + productItem.getWeightType() + "*\n" +
                            "📦 Eng kam sotiladigan karobkadagi miqdori: *" +
                            String.format("%,.2f", productItem.getAllAmount()) + " " + productItem.getWeightType() + "*\n" +
                            "🗂️ Sotish turi: *" + sellType + "*\n\n\n" +
                            "Tahrir qilish kerak bo'lgan qismni tanlang:");
                    sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("🔠 Nomi 🔠", "/universal/product/update/name/" + productItem.getKeyName()),
                                    InlineButtonUtil.button("🗂️️️ Bo'limi 🗂️", "/universal/product/update/category/" + productItem.getKeyName())
                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("💵 Narxi 💵", "/universal/product/update/price/" + productItem.getKeyName()),
                                    InlineButtonUtil.button("💵️️ Umumiy narxi 💵️", "/universal/product/update/allprice/" + productItem.getKeyName())
                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("📦 Midori 📦", "/universal/product/update/amount/" + productItem.getKeyName()),
                                    InlineButtonUtil.button("📦️️ Umumiy miqdori 📦️", "/universal/product/update/allamount/" + productItem.getKeyName())
                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("🗂️ Sotish turi 🗂️", "/universal/product/update/selltype/" + productItem.getKeyName()),
                                    InlineButtonUtil.button("⬅️️ Orqaga️", "/universal/product/update/" + productItem.getKeyName())
                            ),
                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                    )));
                    break;
                case UPDATE_ALL_AMOUNT:
                    try {
                        productItem.setAllAmount(Double.parseDouble(text));
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                        sendMessage.setText("Mahsulot tahrirlandi!");
                        this.productRepository.productMap.put(productItem.getKeyName(), productItem);
                        this.productTempMap.remove(chatId);
                    } catch (Exception e) {
                        sendMessage.setText("Faqat son kiriting!");
                    }
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case UPDATE_AMOUNT:
                    try {
                        productItem.setAmount(Double.parseDouble(text));
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                        sendMessage.setText("Mahsulot tahrirlandi!");
                        this.productRepository.productMap.put(productItem.getKeyName(), productItem);
                        this.productTempMap.remove(chatId);
                    } catch (Exception e) {
                        sendMessage.setText("Faqat son kiriting!");
                    }
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case UPDATE_PRICE:
                    try {
                        productItem.setPrice(Double.parseDouble(text));
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                        sendMessage.setText("Mahsulot tahrirlandi!");
                        this.productRepository.productMap.put(productItem.getKeyName(), productItem);
                        this.productTempMap.remove(chatId);
                    } catch (Exception e) {
                        sendMessage.setText("Faqat son kiriting!");
                    }
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case UPDATE_ALL_PRICE:
                    try {
                        productItem.setAllPrice(Double.parseDouble(text));
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                        sendMessage.setText("Mahsulot tahrirlandi!");
                        this.productRepository.productMap.put(productItem.getKeyName(), productItem);
                        this.productTempMap.remove(chatId);
                    } catch (Exception e) {
                        sendMessage.setText("Faqat son kiriting!");
                    }
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case UPDATE_NAME:
                    try {
                        productItem.setName(text);
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                        sendMessage.setText("Mahsulot tahrirlandi!");
                        this.productRepository.productMap.put(productItem.getKeyName(), productItem);
                        this.productTempMap.remove(chatId);
                    } catch (Exception e) {
                        sendMessage.setText("Faqat son kiriting!");
                    }
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case UPDATE_WEIGHT_TYPE:
                    try {
                        productItem.setWeightType(text);
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                        sendMessage.setText("Mahsulot tahrirlandi!");
                        this.productRepository.productMap.put(productItem.getKeyName(), productItem);
                        this.productTempMap.remove(chatId);
                    } catch (Exception e) {
                        sendMessage.setText("Faqat son kiriting!");
                    }
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case UPDATE_COUNT:
                    try {
                        productItem.setCount(Double.parseDouble(text));
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button(" ⬅️ Orqaga", "/universal/product/update/content/" + productItem.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                        sendMessage.setText("Mahsulot tahrirlandi!");
                        this.productRepository.productMap.put(productItem.getKeyName(), productItem);
                        this.productTempMap.remove(chatId);
                    } catch (Exception e) {
                        sendMessage.setText("Faqat son kiriting!");
                    }
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;
                case PHOTO:
                    try {
                        sellType = this.getProductSellType(productItem);
                        productItem.setPhotoId(text);
                        sendPhoto.setPhoto(text);
                        sendPhoto.setChatId(chatId);
                        sendPhoto.setParseMode("Markdown");
                        sendPhoto.setCaption("* ---- 📝 Qo'shilayotgan mahsulot 	📝 ---- *\n" +
                                "🔠 Nomi: *" + productItem.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + productItem.getKeyName() + "*\n" +
                                "🗂️ Bo'lim: *" + productItem.getCategoryName() + "*\n" +
                                "💵 Narxi: *" + String.format("%,.2f", productItem.getPrice()) + " so'm*\n" +
                                "\t\uD83D\uDD22 Soni: *" +
                                String.format("%,.0f", productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount())) + " ta (karobkada), " +
                                String.format("%,.0f", productItem.getCount()) + " ta (donada) "
                                + "*\n" +
                                "💵 Eng kam sotiladigan umumiy narxi: *" + String.format("%,.2f", productItem.getAllPrice()) + " so'm*\n" +
                                "📦 Miqdori: *" + String.format("%,.2f", productItem.getAmount()) + " "
                                + productItem.getWeightType() + "*\n" +
                                "📦 Eng kam sotiladigan karobkadagi miqdori: *" +
                                String.format("%,.0f", productItem.getAllAmount()) + " " + productItem.getWeightType() + "*\n" +
                                "🗂️ Sotish turi: *" + sellType + "*\n\n\n" +
                                "Tasdiqlaysizmi❓(Ha✅/ Yo'q❌)\n");
                        codeMessage.setSendPhoto(sendPhoto);
                        sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                        codeMessage.setType(CodeMessageType.PHOTO);
                        productItem.setProductItemType(ProductItemType.CONFIRMED);
                        return codeMessage;
                    } catch (Exception e) {
                        sendMessage.setText("Xatolik! Qaydatadan urinib ko'ring");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(InlineButtonUtil.collection(
                                InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel")))));
                        codeMessage.setSendMessage(sendMessage);
                        codeMessage.setType(CodeMessageType.MESSAGE);
                        return codeMessage;
                    }
                case CONFIRMED:
                    if (text.toLowerCase().equals("ha")) {
                        sendMessage.setText("Mahsulot qo'shildi!");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("♒ Ro'yhati ♒", "/universal/product/list")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("➕ Qo'shish ➕", "/universal/product/add")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("➕ File orqali qo'shish ➕", "/universal/product/addByFile")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("✏️ Tahrirlash ✏️", "/universal/product/update")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\uD83D\uDDD1️ O'chirish \uD83D\uDDD1️", "/universal/product/delete")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\t\uD83D\uDC40 Ko'rish \t\uD83D\uDC40", "/universal/product/see")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("✳️ Savdoga qo'yish/olish ✴️", "/universal/product/activate")
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                        productItem.setProductItemType(ProductItemType.FINISHED);
                        productItem.setActive(true);
                        this.productRepository.addProduct(productItem.getKeyName(), productItem);
                        this.productTempMap.remove(productItem.getId());
                    } else if (text.toLowerCase().equals("yo'q")) {
                        this.productTempMap.remove(productItem.getId());
                        sendMessage.setText("Mahsulot qo'shish bekor qilindi!");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("♒ Ro'yhati ♒", "/universal/product/list")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("➕ Qo'shish ➕", "/universal/product/add")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("✏️ Tahrirlash ✏️", "/universal/product/update")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\uD83D\uDDD1️ O'chirish \uD83D\uDDD1️", "/universal/product/delete")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\t\uD83D\uDC40 Ko'rish \t\uD83D\uDC40", "/universal/product/see")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("✳️ Savdoga qo'yish/olish ✴️", "/universal/product/activate")
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                )));
                    } else {
                        sendMessage.setText("\nTasdiqlaysizmi❓(Ha✅/ Yo'q❌)\n");
                    }
                    break;
                case UPDATE_ACTIVATE:
                    ProductItem activeProduct = this.productRepository.productMap.get(text);
                    if (activeProduct != null) {
                        sendPhoto.setPhoto(activeProduct.getPhotoId());
                        sendPhoto.setChatId(chatId);
                        sendPhoto.setParseMode("Markdown");
                        sendPhoto.setCaption("* ---- 📝 Mahsulot info 📝 ---- *\n" +
                                "🔠 Nomi: *" + activeProduct.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + activeProduct.getKeyName() + "*\n" +
                                "🗂️ Bo'lim: *" + activeProduct.getCategoryName() + "*\n" +
                                "\t\uD83D\uDD22 Soni: *" +
                                String.format("%,.0f", activeProduct.getCount() / (activeProduct.getAllAmount() / activeProduct.getAmount())) + " ta (karobkada), " +
                                String.format("%,.0f", activeProduct.getCount()) + " ta (donada) "
                                + "*\n" +
                                "\t\uD83C\uDD97 Holati: *" + (activeProduct.isActive() ? "\uD83D\uDD06 Savdoda" : "\uD83D\uDEAB Savdoda emas") + "*\n\n" +
                                "\n*" + (activeProduct.isActive() ? "\uD83D\uDD06 Savdodan olishni" : "\uD83D\uDEAB Savdoga qo'yishni") + " istaysizmi!\n*");
                        sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("✅ Ha ✅", "/universal/product/activate/" + activeProduct.getKeyName())
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("❌ Yo'q ❌", "/universal/product/list")
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("⬅️ Menyuga qaytish", "/adminPanel"))
                                )));
                        codeMessage.setSendPhoto(sendPhoto);
                        codeMessage.setType(CodeMessageType.PHOTO);
                        return codeMessage;
                    } else {
                        sendMessage.setText("\n ❌ Bunday mahsulot yo'q ❌ \n");
                        codeMessage.setSendMessage(sendMessage);
                        codeMessage.setType(CodeMessageType.MESSAGE);
                        return codeMessage;
                    }
                case UPDATE_SELECT:
                    ProductItem updateProduct = this.productRepository.productMap.get(text);
                    if (updateProduct != null) {
                        sellType = this.getProductSellType(updateProduct);
                        sendPhoto.setPhoto(updateProduct.getPhotoId());
                        sendPhoto.setChatId(chatId);
                        sendPhoto.setParseMode("Markdown");
                        sendPhoto.setCaption(
                                "* ---- Mahsulot ---- *\n\n" +
                                        "🔠 Nomi: *" + updateProduct.getName() + "*\n" +
                                        "🔑 Kalit so'z: *" + updateProduct.getKeyName() + "*\n" +
                                        "🗂️ Bo'lim: *" + updateProduct.getCategoryName() + "*\n" +
                                        "📦 Miqdori: *" + (String.format("%,.2f", updateProduct.getAmount()) + " " + updateProduct.getWeightType()) + " *\n" +
                                        "📦 Umumiy miqdori: *" + (String.format("%,.2f", updateProduct.getAllAmount()) + " " + updateProduct.getWeightType()) + " *\n" +
                                        "💵 Narxi: *" + (String.format("%,.2f", updateProduct.getPrice())) + " so'm*\n" +
                                        "💵 Umumiy narxi: *" + (String.format("%,.2f", updateProduct.getAllPrice())) + " so'm*\n" +
                                        "\t\uD83D\uDD22 Soni: *" +
                                        String.format("%,.0f", updateProduct.getCount() / (updateProduct.getAllAmount() / updateProduct.getAmount())) + " ta (karobkada), " +
                                        String.format("%,.0f", updateProduct.getCount()) + " ta (donada) "
                                        + "*\n" +
                                        "\t\uD83C\uDD97 Holati: *" + (updateProduct.isActive() ? " savdoda" : "savdoda emas") + "*" +
                                        "🗂️ Sotish turi: *" + sellType + "*" +
                                        "\n\n\n*Tahrirlash uchun birini tanlang?*"
                        );
                        sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\uD83E\uDDFE Mahsulot matni \uD83E\uDDFE", "/universal/product/update/content/" + updateProduct.getKeyName())
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\uD83D\uDDBC️ Mahsulot rasmi \uD83D\uDDBC️", "/universal/product/update/photo/" + updateProduct.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("⬅️ Menyuga qaytish", "/adminPanel"))
                                )));
                    } else {
                        sendMessage.setText("Bunday mahsulot toplimadi!");
                        codeMessage.setSendMessage(sendMessage);
                        codeMessage.setType(CodeMessageType.MESSAGE);
                        return codeMessage;
                    }
                    codeMessage.setSendPhoto(sendPhoto);
                    codeMessage.setType(CodeMessageType.PHOTO);
                    return codeMessage;
                case ACITVE:

                    break;
                case GET_PHONE:
                    OrderItem userOrder = this.orderRepository.getUserOrderList(chatId);
                    if (userOrder != null) {
                        userOrder.setPhoneNumber(text);
                        this.orderRepository.addOrder(userOrder.getId(), userOrder);

                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        replyKeyboardMarkup.setSelective(true);
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setOneTimeKeyboard(true);

                        // new list
                        List<KeyboardRow> keyboard = new ArrayList<>();

                        // first keyboard line
                        KeyboardRow keyboardFirstRow = new KeyboardRow();
                        KeyboardButton keyboardButton = new KeyboardButton();
                        keyboardButton.setText("\uD83D\uDCCD Manzilni jo'natish! \uD83D\uDCCD");
                        keyboardButton.setRequestLocation(true);
                        keyboardFirstRow.add(keyboardButton);
                        // add array to list
                        keyboard.add(keyboardFirstRow);
                        // add list to our keyboard
                        replyKeyboardMarkup.setKeyboard(keyboard);
                        productItem.setProductItemType(ProductItemType.GET_LOCATION);
                        sendMessage.setText("\uD83D\uDCCD Buyurtma qilish uchun xaritadan manzilingizni jo'nating:");
                    } else {
                        sendMessage.setText("<b>Xatolik</b> ‼️");
                    }
                    sendMessage.setParseMode("HTML");
                    break;
                case GET_LOCATION:
                    OrderItem userOrders = this.orderRepository.getUserOrderList(chatId);
                    if (userOrders != null) {
                        String[] location = text.split("/");
                        if (location.length == 2) {
                            userOrders.setLan(location[0]);
                            userOrders.setLat(location[1]);
                            this.orderRepository.addOrder(userOrders.getId(), userOrders);

                            sendMessage.setText("<b>Buyurtma qilishni tasdiqlaysizmi</b>❓");
                            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                    InlineButtonUtil.collection(
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("\t\uD83D\uDE9A Buyurtma qilish \t\uD83D\uDE9A", "/universal/order/confirmed/" + userOrders.getId())
                                            ),
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("❌ Yo'q ❌", "menu")
                                            )
                                    )));
                            productItem.setProductItemType(ProductItemType.FINISHED);
                            this.productTempMap.remove(productItem.getId());
                        } else {
                            sendMessage.setText("<b>Xatolik</b> ‼️");
                        }
                    } else {
                        sendMessage.setText("<b>Xatolik</b> ‼️");
                    }
                    sendMessage.setParseMode("HTML");
                    break;
                case DELETE:
                    ProductItem deleteProduct = this.productRepository.getProduct(text);
                    if (deleteProduct != null) {
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("✅️️️ Ha ✅", "/universal/product/delete/" + deleteProduct.getKeyName() + "/ha")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("❌ Yo'q ❌", "/universal/product/select/" + deleteProduct.getKeyName())
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("⬅️️ Menyu", "/adminPanel"))
                                )));
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setText(
                                "Siz *" + deleteProduct.getName() + "* nomli,\n" +
                                        "*" + deleteProduct.getKeyName() + "* raqamli, mahsulotni o'chirishni istaysizmi?\n");
                        this.productTempMap.remove(deleteProduct.getId());
                        codeMessage.setType(CodeMessageType.MESSAGE);
                        codeMessage.setSendMessage(sendMessage);
                        return codeMessage;
                    } else {
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setText("*Bunday mahsulot topilmadi!*");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("Mahsulotlar", "/universal/product/list")
                                        ),
                                        InlineButtonUtil.row(InlineButtonUtil.button("⬅️️ Menyu", "/adminPanel"))
                                )));
                    }
                    break;
                case UPDATE_PHOTO:
                    for (ProductItem product1 : productTempMap.values()) {
                        if (product1.getProductItemType().equals(ProductItemType.UPDATE_PHOTO)) {
                            product1.setPhotoId(text);
                            product1.setProductItemType(ProductItemType.FINISHED);
                            productRepository.productMap.put(product1.getKeyName(), product1);
                            sendMessage.setParseMode("Markdown");
                            sendMessage.setText("\n*Rasm saqlandi!*\n");
                            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                    InlineButtonUtil.collection(
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("♒ Ro'yhati ♒", "/universal/product/list")
                                            ),
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("➕ Qo'shish ➕", "/universal/product/add")
                                            ),
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("➕ File orqali qo'shish ➕", "/universal/product/addByFile")
                                            ),
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("✏️ Tahrirlash ✏️", "/universal/product/update")
                                            ),
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("\uD83D\uDDD1️ O'chirish \uD83D\uDDD1️", "/universal/product/delete")
                                            ),
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("\t\uD83D\uDC40 Ko'rish \t\uD83D\uDC40", "/universal/product/see")
                                            ),
                                            InlineButtonUtil.row(
                                                    InlineButtonUtil.button("✳️ Savdoga qo'yish/olish ✴️", "/universal/product/activate")
                                            ),
                                            InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "/adminPanel"))
                                    )));
                            codeMessage.setType(CodeMessageType.MESSAGE);
                            codeMessage.setSendMessage(sendMessage);
                            return codeMessage;
                        }
                    }
                    sendMessage.setText("Xatolik! Qaytadan urinib ko'ring!");
                    codeMessage.setType(CodeMessageType.MESSAGE);
                    codeMessage.setSendMessage(sendMessage);
                    return codeMessage;

                case SEE:
                    ProductItem product = this.productRepository.getProduct(text);
                    if (product != null) {
                        sendPhoto.setChatId(chatId);
                        sendPhoto.setPhoto(product.getPhotoId());
                        sendPhoto.setParseMode("Markdown");
                        sendPhoto.setCaption("* ---- Mahsulot ---- *\n\n" +
                                "🔠 Nomi: *" + product.getName() + "*\n" +
                                "🔑 Kalit so'z: *" + product.getKeyName() + "*\n" +
                                "🗂️ Bo'lim: *" + product.getCategoryName() + "*\n" +
                                "📦 Miqdori: *" + (String.format("%,.2f", product.getAmount()) + " " + product.getWeightType()) + " *\n" +
                                "📦 Eng kam sotiladigan karobkadagi miqdori: *" + (String.format("%,.2f", product.getAllAmount()) + " " + product.getWeightType()) + " *\n" +
                                "💵 Narxi: *" + (String.format("%,.2f", product.getPrice())) + " so'm*\n" +
                                "💵 Eng kam sotiladigan karobkadagi narxi: *" + (String.format("%,.2f", product.getAllPrice())) + " so'm*\n" +
                                "\t\uD83D\uDD22 Soni: *" +
                                String.format("%,.0f", product.getCount() / (product.getAllAmount() / product.getAmount())) + " ta (karobkada), " +
                                String.format("%,.0f", product.getCount()) + " ta (donada) "
                                + "*\n" +
                                "\t\uD83C\uDD97 Holati: *" + (product.isActive() ? " savdoda" : "savdoda emas") + "*" +
                                "🗂️ Sotish turi: *" + this.getProductSellType(product) + "*");
                        sendPhoto.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\t\uD83D\uDCDD Tahrirlash \t\uD83D\uDCDD", "/universal/product/update/content/" + product.getKeyName())
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\t\uD83D\uDD12 Holatini o'zgartirish \t\uD83D\uDD13", "/universal/product/activate/" + product.getKeyName())
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("\uD83D\uDDD1️ O'chirish \uD83D\uDDD1️", "/universal/product/delete/" + product.getKeyName())
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("⬅️️ Orqaga", "/universal/page/" + product.getCategoryName() + "/admin/1"),
                                                InlineButtonUtil.button("✅ Menu ✅", "/universal/product")
                                        )
                                )));
                        this.productTempMap.remove(product.getId());
                        codeMessage.setSendPhoto(sendPhoto);
                        codeMessage.setType(CodeMessageType.PHOTO);
                        return codeMessage;
                    } else {
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setText("*Bunday mahsulot topilmadi!*");
                        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                                InlineButtonUtil.collection(
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("Mahsulotlar", "/universal/product/list")
                                        ),
                                        InlineButtonUtil.row(
                                                InlineButtonUtil.button("✅ Menu ✅", "/universal/product")
                                        )
                                )));
                    }
                    break;
            }
            codeMessage.setSendMessage(sendMessage);
            codeMessage.setType(CodeMessageType.MESSAGE);
        }

        return codeMessage;
    }

    private InlineKeyboardMarkup getProductPageKeyBoard(String userType, String productType, Integer pageNum) {
        List<List<InlineKeyboardButton>> collection = InlineButtonUtil.collection();
        List<InlineKeyboardButton> row = InlineButtonUtil.row();
        if (this.productPageMap.size() > 0) {
            if (this.productPageMap.get(pageNum).size() > 0) {
                for (ProductItem productItem : this.productPageMap.get(pageNum)) {
                    if (!productItem.getCategoryName().equals(productType)) {
                        return this.getProductKeyBoard(productType, userType);
                    }
                    if (userType.equals("admin") && productItem.getCategoryName().equals(productType)) {
                        row.add(InlineButtonUtil
                                .button(productItem.getName(),
                                        "/universal/product/select/" + productItem.getKeyName()));
                        collection.add(row);
                        row = new ArrayList<>();
                    } else if (productItem.getCategoryName().equals(productType)
                            && productItem.isActive()
                            && productItem.getCount() > 0 && userType.equals("user")) {
                        row.add(InlineButtonUtil
                                .button(productItem.getName(),
                                        "/universal/order/product/" + productItem.getKeyName()));
                        collection.add(row);
                        row = new ArrayList<>();
                    }
                }
//            collection.add(InlineButtonUtil.row(InlineButtonUtil.button("⬅️ Orqaga", (userType.equals("admin") ? "/adminPanel" : "/universal/user/"+))));
//                if (productItem.getCategoryName().equals(productType))
                for (int i = 0; i < this.productPageMap.size(); i++) {
                    if (i < 5) {
                        row.add(InlineButtonUtil
                                .button("" + (i + 1),
                                        "/universal/page/" + productType + "/" + userType + "/" + (i + 1)));
                        if (row.size() >= 5) {
                            collection.add(row);
                            row = new ArrayList<>();
                        }
                    } else if (i > 4) {
                        row.add(InlineButtonUtil
                                .button("" + (i + 1),
                                        "/universal/page/" + productType + "/" + userType + "/" + (i + 1)));
                        if (row.size() >= 5) {
                            collection.add(row);
                            row = new ArrayList<>();
                        }
                    } else if (i < 15 && i > 9) {
                        row.add(InlineButtonUtil
                                .button("" + (i + 1),
                                        "/universal/page/" + productType + "/" + userType + "/" + (i + 1)));
                        if (row.size() >= 5) {
                            collection.add(row);
                            row = new ArrayList<>();
                        }
                    } else if (i > 14 && i < 20) {
                        row.add(InlineButtonUtil
                                .button("" + (i + 1),
                                        "/universal/page/" + productType + "/" + userType + "/" + (i + 1)));
                        if (row.size() >= 5) {
                            collection.add(row);
                            row = new ArrayList<>();
                        }
                    }
                }
                if (row.size() > 0) {
                    collection.add(row);
                    row = new ArrayList<>();
                }
                collection.add(InlineButtonUtil.row(InlineButtonUtil.button("⬅️ Menyuga qaytish", (userType.equals("admin") ? "/adminPanel" : "menu"))));
                return InlineButtonUtil.keyboard(collection);
            }
        }
        return null;
    }

    private List<ProductItem> makeOrderByProductReport(String date, String dateFormat) {
        List<ProductItem> productItemList = new LinkedList<>();
        boolean isHave = false;
        Collection<OrderItem> values = this.orderRepository.orderMap.values();

        for (OrderItem order : values) {
            if (order.getOrderItemType().equals(OrderItemType.FINISHED)) {
                boolean dateFormatB = false;
                if (dateFormat.length() > 0) {
                    switch (dateFormat) {
                        case "year":
                            dateFormatB = date.equals(yearDateFormat.format(order.getDate()));
                            break;
                        case "month":
                            dateFormatB = date.equals(monthDateFormat.format(order.getDate()));
                            break;
                        case "day":
                            dateFormatB = date.equals(dayDateFormat.format(order.getDate()));
                            break;
                    }
                } else {
                    dateFormatB = true;
                }
                if (dateFormatB) {
                    for (ProductItem productItem : order.getProductItems()) {
                        isHave = false;
                        if (productItemList.size() > 0) {
                            for (ProductItem productItem1 : productItemList) {
                                if (productItem.getKeyName().equals(productItem1.getKeyName())
                                        && productItem.getPrice() == productItem1.getPrice()
                                        && productItem.getAllAmount() == productItem1.getAllAmount()
                                        && productItem.getAllPrice() == productItem1.getAllPrice()
                                        && productItem.getAmount() == productItem1.getAmount()) {
                                    productItem1.setTotalPrice(productItem1.getTotalPrice() + productItem.getTotalPrice());
                                    productItem1.setCount(productItem1.getCount() + productItem.getCount());
                                    isHave = true;
                                    break;
                                }
                            }
                        }
                        if (!isHave) {
                            productItemList.add(productItem);
                        }
                    }
                }
            }
        }
        return productItemList;
    }

    private InlineKeyboardMarkup getProductKeyBoard(String productType, String userType) {
        List<List<InlineKeyboardButton>> collection = InlineButtonUtil.collection();
        List<InlineKeyboardButton> row = InlineButtonUtil.row();
        this.productPageMap = new HashMap<>();
        if (this.productRepository.productMap.size() > 0) {
            List<ProductItem> productItems = new ArrayList<>();
            int pageNum = 1;
            for (ProductItem productItem : this.productRepository.productMap.values()) {
                if (productItems.size() == 2) {
                    productPageMap.put(pageNum, productItems);
                    productItems = new ArrayList<>();
                    pageNum++;
                }
                if (userType.equals("admin") && productItem.getCategoryName().equals(productType)) {
                    productItems.add(productItem);
                }
                if (productItem.getCategoryName().equals(productType)
                        && productItem.isActive()
                        && productItem.getCount() > 0 && userType.equals("user")) {
                    productItems.add(productItem);
                }
            }
            if ((this.productPageMap.size() == 0 && this.productRepository.productMap.size() > 0)
                    || this.productPageMap.size() > 0) {
                productPageMap.put(pageNum, productItems);
                productItems = new ArrayList<>();
                pageNum++;
            }
            for (ProductItem productItem : this.productPageMap.get(1)) {
                if (userType.equals("admin") && productItem.getCategoryName().equals(productType)) {
                    row.add(InlineButtonUtil
                            .button(productItem.getName(),
                                    "/universal/product/select/" + productItem.getKeyName()));
                    collection.add(row);
                    row = new ArrayList<>();
                } else if (productItem.getCategoryName().equals(productType)
                        && productItem.isActive()
                        && productItem.getCount() > 0 && userType.equals("user")) {
                    row.add(InlineButtonUtil
                            .button(productItem.getName(),
                                    "/universal/order/product/" + productItem.getKeyName()));
                    collection.add(row);
                    row = new ArrayList<>();
                }
            }
//            collection.add(InlineButtonUtil.row(InlineButtonUtil.button("⬅️ Orqaga", (userType.equals("admin") ? "/adminPanel" : "/universal/user/"+))));
            for (int i = 0; i < this.productPageMap.size(); i++) {
                if (i < 5) {
                    row.add(InlineButtonUtil
                            .button("" + (i + 1),
                                    "/universal/page/" + productType + "/" + userType + "/" + (i + 1)));
                    if (row.size() >= 5) {
                        collection.add(row);
                        row = new ArrayList<>();
                    }
                } else if (i > 4) {
                    row.add(InlineButtonUtil
                            .button("" + (i + 1),
                                    "/universal/page/" + productType + "/" + userType + "/" + (i + 1)));
                    if (row.size() >= 5) {
                        collection.add(row);
                        row = new ArrayList<>();
                    }
                } else if (i < 15 && i > 9) {
                    row.add(InlineButtonUtil
                            .button("" + (i + 1),
                                    "/universal/page/" + productType + "/" + userType + "/" + (i + 1)));
                    if (row.size() >= 5) {
                        collection.add(row);
                        row = new ArrayList<>();
                    }
                } else if (i > 14 && i < 20) {
                    row.add(InlineButtonUtil
                            .button("" + (i + 1),
                                    "/universal/page/" + productType + "/" + userType + "/" + (i + 1)));
                    if (row.size() >= 5) {
                        collection.add(row);
                        row = new ArrayList<>();
                    }
                }
            }
            if (row.size() > 0) {
                collection.add(row);
                row = new ArrayList<>();
            }
            collection.add(InlineButtonUtil.row(InlineButtonUtil.button("⬅️ Menyuga qaytish", (userType.equals("admin") ? "/adminPanel" : "menu"))));
            return InlineButtonUtil.keyboard(collection);
        }
        return InlineButtonUtil.keyboard(
                InlineButtonUtil.collection(
                        InlineButtonUtil.row(InlineButtonUtil.button("⬅️ Menyuga qaytish", (userType.equals("admin") ? "/adminPanel" : "menu")))
                ));

    }

    public Map<Long, ProductItem> getProductItemMap() {
        return productTempMap;
    }

    public void orderProductBasket(StringBuilder stringBuilder, OrderItem orderList, SendMessage sendMessage) {
        int countNum = 1;
        double allPrice = 0;
        stringBuilder.append("<b> ---- \t\uD83D\uDCE4 Buyurtmalar ro'yhati \t\uD83D\uDCE4 ---- </b>");
        for (ProductItem dto : orderList.getProductItems()) {
            int blokCount = 0;
            double count = 0.00;

            if (dto.getCount() > (dto.getAllAmount() / dto.getAmount())) {
                blokCount = (int) (dto.getCount() / (dto.getAllAmount() / dto.getAmount()));
                count = dto.getCount() % (dto.getAllAmount() / dto.getAmount());
            } else if (dto.getCount() == (dto.getAllAmount() / dto.getAmount())) {
                blokCount = (int) (dto.getCount() / (dto.getAllAmount() / dto.getAmount()));
            } else if (dto.getCount() < (dto.getAllAmount() / dto.getAmount())) {
                count = dto.getCount();
            }
            stringBuilder.append("\n\n");
            stringBuilder.append("<b>" + countNum + ". Mahsulot nomi:</b> <i>" + dto.getName() + "("
                    + dto.getKeyName() + ")</i>");
            stringBuilder.append("\n");
            stringBuilder.append("\uD83D\uDCB8 <b>Narxi: " + String.format("%,.2f", dto.getTotalPrice()) + " so'm</b> \n");
            stringBuilder.append((count > 0 ? String.format("%,.0f", count) + " (дона) x " + String.format("%,.2f", dto.getPrice()) + " (so'm) = <b>" +
                    String.format("%,.2f", (count * dto.getPrice())) + "  so'm</b>\n" : ""));
            stringBuilder.append((blokCount > 0 ? blokCount + " (кор.) x " + String.format("%,.2f", dto.getAllPrice()) + " (so'm) = <b>" +
                    String.format("%,.2f", (blokCount * dto.getAllPrice())) + "  so'm</b>\n" : ""));
            stringBuilder.append("-----------------------------------------\n");
            stringBuilder.append("\n\n");
            allPrice += dto.getTotalPrice();
            countNum++;
        }
        String allPriceStr = "" + allPrice;
        int i = allPriceStr.indexOf(".");

        if (Double.parseDouble(allPriceStr) >= 100000.00) {
            stringBuilder.append("-----------------------------------------\n");
            stringBuilder.append("<b>\t\uD83D\uDCB0 Umumiy narxi: <i>" + (i > 0 ? String.format("%,.2f", Double.parseDouble(allPriceStr)) : allPriceStr) + "</i> so'm </b>\n\n");
        } else {
            stringBuilder.append("-----------------------------------------\n");
            stringBuilder.append("<b>\t\uD83D\uDCB0 Umumiy narxi: <del>" + (i > 0 ? String.format("%,.2f", Double.parseDouble(allPriceStr)) : allPriceStr) + "</del> so'm </b>\n\n");
            stringBuilder.append("<b>\t\uD83D\uDCB0 Umumiy narxi: 100,000 so'mda kam bo'lamsiligi kerak!</b>");
        }

        sendMessage.setText(stringBuilder.toString());
        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                InlineButtonUtil.collection(
                        InlineButtonUtil.row(
                                InlineButtonUtil.button(" \t\uD83D\uDE98 Buyurtma qilish", Double.parseDouble(allPriceStr) >= 100000.00 ? "/universal/order/getPhoneNumber" : "/universal/basket")
                        ),
                        InlineButtonUtil.row(
                                InlineButtonUtil.button(" ⬅️ Menyuga qaytish", "menu")
                        ),
                        InlineButtonUtil.row(
                                InlineButtonUtil.button(" \t\uD83D\uDD04 Savatni tozalash", "/universal/basket/clear")
                        ),
                        InlineButtonUtil.row(
                                InlineButtonUtil.button(" ❌ Mahsulotni bekor qilish", "/universal/basket/product/clear")
                        )

                )));
    }

    public InlineKeyboardMarkup resButton(String path, String productKey) {
        List<List<InlineKeyboardButton>> collection = InlineButtonUtil.collection();
        List<InlineKeyboardButton> row = InlineButtonUtil.row();
        List<InlineKeyboardButton> row2 = InlineButtonUtil.row();

        ProductItem productItem = this.productRepository.productMap.get(productKey);
        String[] split = path.split("/");
        if (split[4].equals("blok")) {
            int buttonCount = Integer.parseInt(String.valueOf(productItem
                    .getCount() > 10 ? 10 : (String.valueOf(productItem.getCount())
                    .substring(0, String.valueOf(productItem.getCount()).indexOf(".")))));
            int oneRowCount = (buttonCount / 2 == 0 ? buttonCount / 2 : (buttonCount + 1) / 2);
            for (int i = 1; i <= buttonCount; i++) {
                if (i <= oneRowCount) {
                    row.add(InlineButtonUtil.button("" + i, path + productKey + "/" + i));
                } else {
                    row2.add(InlineButtonUtil.button("" + i, path + productKey + "/" + i));
                }
            }
        } else {
            int buttonCount = Integer.parseInt(String.valueOf(productItem
                    .getCount() * productItem.getAllAmount() > 10 ? 10 :
                    (String.valueOf(productItem.getCount() * productItem.getAllAmount())
                            .substring(0, String.valueOf(productItem.getCount() * productItem.getAllAmount()).indexOf(".")))));
            int oneRowCount = (buttonCount / 2 == 0 ? buttonCount / 2 : (buttonCount + 1) / 2);
            for (int i = 1; i <= buttonCount; i++) {
                if (i <= oneRowCount) {
                    row.add(InlineButtonUtil.button("" + i, path + productKey + "/" + i));
                } else {
                    row2.add(InlineButtonUtil.button("" + i, path + productKey + "/" + i));
                }
            }
        }
        collection.add(row);
        collection.add(row2);

        collection.add(InlineButtonUtil.row(InlineButtonUtil.button(" ⬅️ Orqaga", (productItem.getProductSellType().equals("all") ? ("/universal/order/product/" + productKey) : "/universal/user/" + productItem.getCategoryName()))));

        collection.add(InlineButtonUtil.row(InlineButtonUtil.button("❇️️️ Menyu ❇️", "menu")));
        return InlineButtonUtil.keyboard(collection);
    }

    public String getProductSellType(ProductItem product) {
        String sellType = "";
        switch (product.getProductSellType()) {
            case "dona":
                sellType = "donalab";
                break;
            case "blok":
                sellType = "karobka";
                break;
            case "all":
                sellType = "donaga, karobkaga";
                break;
        }
        return sellType;
    }

    public boolean getUser(Message message) {
        if (this.userRepository.userMap.get(message.getChatId()) == null) {
            UserItem userItem = new UserItem();
            userItem.setName(message.getFrom().getFirstName());
            userItem.setUsername(message.getFrom().getUserName());
            userItem.setDate(new Date());
            userItem.setId(String.valueOf(message.getChatId()));
            this.userRepository.userMap.put(message.getChatId(), userItem);
            return true;
        }
        return false;

    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "t.me/buyurtma_test_bot";
    }

    @Override
    public String getBotToken() {
        return "1117431038:AAFHCZVhC47wFLRPbI9ca_paHygly_5h11M";
    }
}
