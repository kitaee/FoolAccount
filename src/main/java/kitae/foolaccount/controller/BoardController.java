package kitae.foolaccount.controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class BoardController {

    @GetMapping("/boards")
    public String boardView(){

        final String domesticStockUrl = "https://kr.investing.com/stock-screener/?sp=country::11|sector::a|industry::a|equityType::a%3Ceq_market_cap;1";
        Connection domesticStock = Jsoup.connect(domesticStockUrl);
        ArrayList<String> domesticStockList = new ArrayList<>();

        try{
            Document domesticCurrency = domesticStock.get();
            Elements domesticCurrencyInformationList = domesticCurrency.select("#resultsTable tbody tr");
            System.out.println(domesticCurrencyInformationList);

        }catch(
                IOException e){
            e.printStackTrace();
        }




        return "board";
    }
}
