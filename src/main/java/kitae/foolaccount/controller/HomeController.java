package kitae.foolaccount.controller;

import kitae.foolaccount.domain.Member;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class HomeController {

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/main")
    public String backHome(){
        return "main";
    }


    @GetMapping("/list")
    public String naverStockPriceCrawling(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute("member");
        if(member!=null) {
//            System.out.println(member.getName());
            model.addAttribute("member_id", member.getId());
            model.addAttribute("name", member.getName());
            return "login_complete";
        }

        ArrayList<String> corporation1 = new ArrayList<>();
        ArrayList<String> price1 = new ArrayList<>();
        ArrayList<String> stock_href = new ArrayList<>();
        ArrayList<String> coin_info = new ArrayList<>();
        ArrayList<String> coin_corporation0 = new ArrayList<>();
        ArrayList<String> coin_price0= new ArrayList<>();
        ArrayList<String> coin_price_currency = new ArrayList<>();
        ArrayList<String> coin_href = new ArrayList<>();



        final String krinvestUrl = "https://kr.investing.com/crypto/";
        Connection conn0 = Jsoup.connect(krinvestUrl);
        final String naverUrl = "https://finance.naver.com/sise/";
        Connection conn1 = Jsoup.connect(naverUrl);

        try {

            Document document0 = conn0.get();
            Elements coin = document0.select("tbody a");
            Elements currency = document0.select(".js-currency-change-24h ");


            for(Element coin_information : coin){
                coin_info.add(coin_information.text());
            }

            for(Element coin_currency : currency){
                coin_price_currency.add(coin_currency.text());
            }

            for(int i=0;i<coin_info.size();i++){
                if(i%2==0){
                    coin_corporation0.add(coin_info.get(i));
                }
                else{
                    coin_price0.add(coin_info.get(i));
                }
            }

            for(Element href : coin){
                coin_href.add(href.attr("abs:href"));
            }
//            System.out.println(coin_href);

            Document document = conn1.get();
            Elements corporation = document.select("#popularItemList li a");
            Elements price = document.select("#popularItemList span");


            for(Element corporation_name : corporation){
                corporation1.add(corporation_name.text());
            }

            for(Element href : corporation){
                stock_href.add(href.attr("abs:href"));
            }
//            System.out.println(stock_href);



            for(Element priceInfo : price){
                price1.add(priceInfo.text());
            }

//            for(int i=0;i<coin_price_currency.size();i++){
//                System.out.println(coin_price_currency.get(i));
//            }



            model.addAttribute("coin0", coin_corporation0.get(0));
            model.addAttribute("coin1", coin_corporation0.get(1));
            model.addAttribute("coin2", coin_corporation0.get(2));
            model.addAttribute("coin3", coin_corporation0.get(3));
            model.addAttribute("coin4", coin_corporation0.get(4));
            model.addAttribute("coin5", coin_corporation0.get(5));

            model.addAttribute("src0", stock_href.get(0));
            model.addAttribute("src1", stock_href.get(1));
            model.addAttribute("src2", stock_href.get(2));
            model.addAttribute("src3", stock_href.get(3));
            model.addAttribute("src4", stock_href.get(4));
            model.addAttribute("src5", stock_href.get(5));

            model.addAttribute("coin_price0", coin_price0.get(0));
            model.addAttribute("coin_price1", coin_price0.get(1));
            model.addAttribute("coin_price2", coin_price0.get(2));
            model.addAttribute("coin_price3", coin_price0.get(3));
            model.addAttribute("coin_price4", coin_price0.get(4));
            model.addAttribute("coin_price5", coin_price0.get(5));

            model.addAttribute("coin_src0", coin_href.get(0));
            model.addAttribute("coin_src1", coin_href.get(2));
            model.addAttribute("coin_src2", coin_href.get(4));
            model.addAttribute("coin_src3", coin_href.get(6));
            model.addAttribute("coin_src4", coin_href.get(8));
            model.addAttribute("coin_src5", coin_href.get(10));

            model.addAttribute("coin_price_change0", coin_price_currency.get(0).charAt(0));
            model.addAttribute("coin_price_change1", coin_price_currency.get(1).charAt(0));
            model.addAttribute("coin_price_change2", coin_price_currency.get(2).charAt(0));
            model.addAttribute("coin_price_change3", coin_price_currency.get(3).charAt(0));
            model.addAttribute("coin_price_change4", coin_price_currency.get(4).charAt(0));
            model.addAttribute("coin_price_change5", coin_price_currency.get(5).charAt(0));



            model.addAttribute("stock0", corporation1.get(0));
            model.addAttribute("stock1", corporation1.get(1));
            model.addAttribute("stock2", corporation1.get(2));
            model.addAttribute("stock3", corporation1.get(3));
            model.addAttribute("stock4", corporation1.get(4));
            model.addAttribute("stock5", corporation1.get(5));

            model.addAttribute("stock_price0", price1.get(0));
            model.addAttribute("stock_price1", price1.get(2));
            model.addAttribute("stock_price2", price1.get(4));
            model.addAttribute("stock_price3", price1.get(6));
            model.addAttribute("stock_price4", price1.get(8));
            model.addAttribute("stock_price5", price1.get(10));

            model.addAttribute("price_change0", price1.get(1));
            model.addAttribute("price_change1", price1.get(3));
            model.addAttribute("price_change2", price1.get(5));
            model.addAttribute("price_change3", price1.get(7));
            model.addAttribute("price_change4", price1.get(9));
            model.addAttribute("price_change5", price1.get(11));




        } catch (
                IOException e){
            e.printStackTrace();
        }


        return "list";
    }

    public static String remainOnlyKorean(String str){
        return str.replaceAll("[^\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F]", "");
    }


}
