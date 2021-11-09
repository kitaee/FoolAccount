package kitae.foolaccount.controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class BoardController {

    @GetMapping("/boards")
    public String boardView(Model model){



        final String domesticStockUrl = "https://kr.investing.com/equities/south-korea";
        Connection domesticStock = Jsoup.connect(domesticStockUrl);
        ArrayList<String> domesticStockList = new ArrayList<>();
        ArrayList<Integer> domesticStockPriceList = new ArrayList<>();
        ArrayList<String> domesticStockInformationList = new ArrayList<>();

        try{
            Document domesticCurrency = domesticStock.get();
            Elements domesticCurrencyInformationList = domesticCurrency.select("#cross_rate_markets_stocks_1 td");
            for(Element company : domesticCurrencyInformationList){
                domesticStockInformationList.add(company.text());
            }
            for(int k=0;k<50;k++){
                domesticStockList.add(domesticStockInformationList.get(10*k+1));
                String string_price = domesticStockInformationList.get(10*k+2).replace(",", "");
                string_price = string_price.replace(".00", "");
                int int_value = Integer.parseInt(string_price);
                domesticStockPriceList.add(int_value);
            }
//            System.out.println(domesticStockList);
//            System.out.println(domesticStockPriceList);

        }catch(
                IOException e){
            e.printStackTrace();
        }


        model.addAttribute("domesticStockList", domesticStockList);
        model.addAttribute("domesticStockPriceList", domesticStockPriceList);

        return "board";
    }
}
