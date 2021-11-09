package kitae.foolaccount.controller;

import kitae.foolaccount.domain.Asset;
import kitae.foolaccount.domain.Member;
import kitae.foolaccount.service.MemberService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
public class MemberController {

    private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @PostMapping("/join")
    public String create(MemberForm form){        // 회원가입을 하고 회원정보를 DB에 저장완료

        Member member = new Member();
        member.setId(form.getId());
        member.setPassword(form.getPassword());
        member.setPassword_confirm_question(form.getPassword_confirm_question());
        member.setPassword_confirm_question_answer(form.getPassword_confirm_question_answer());
        member.setName(form.getName());
        member.setPhone(form.getPhone());
        memberService.join(member);

//        System.out.println(form.getId());
//        System.out.println(form.getPassword());
//        System.out.println(form.getPassword_confirm_question());
//        System.out.println(form.getPassword_confirm_question_answer());
//        System.out.println(form.getName());
//        System.out.println(form.getPhone());
        System.out.println("github b");
        return "redirect:/";
    }

    private Logger logger = LoggerFactory.getLogger(getClass());


    @GetMapping("/{id}")
    public String MyPage(@PathVariable("id") String id, Model model){

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


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        logger.info("mappingPath id={}", id);

        List<Asset> assetList = memberService.findMembers();
        List<Asset> noDuplicateAssetList = new ArrayList<>();
        List<String> usedCorporation = new ArrayList<>();
        Asset new_asset = new Asset();
        Long new_count=0L;
        Long new_price=0L;


        for(int i=0;i<assetList.size();i++){
            if(usedCorporation.contains(assetList.get(i).getCorporation())){
                continue;
            }
            else {
                usedCorporation.add(assetList.get(i).getCorporation());
                for (int j = i; j < assetList.size(); j++) {
                    if (assetList.get(i).getId().equals(assetList.get(j).getId()) && assetList.get(i).getCorporation().equals(assetList.get(j).getCorporation())) {
                        if(assetList.get(j).getAction().equals("buy")){
                            new_count += (assetList.get(j).getCount());
                            new_price +=(assetList.get(j).getCount()*assetList.get(j).getAveragePrice());
                        }
                        else{
                            new_count -= (assetList.get(j).getCount());
                            new_price -=(assetList.get(j).getCount()*assetList.get(j).getAveragePrice());
                        }
                    } else {
                        continue;
                    }
                }
            }
            new_asset.setId(assetList.get(i).getId());
            new_asset.setKind(assetList.get(i).getKind());
            new_asset.setCorporation(assetList.get(i).getCorporation());
            new_asset.setCount(new_count);
            if(new_count!=0){
                new_asset.setAveragePrice(new_price/new_count);
            }
            else{
                new_asset.setAveragePrice(0L);
            }
            noDuplicateAssetList.add(new_asset);
            new_asset = new Asset();
            new_count=0L;
            new_price=0L;
        }


        Long profit=0L;
        Long principal = 0L;
        Long myAssetValue = 0L;
        double d_percent = 0L;

        for(int k=0;k<noDuplicateAssetList.size();k++){
            if(noDuplicateAssetList.get(k).getId().equals(id)){
                int index = domesticStockList.indexOf(noDuplicateAssetList.get(k).getCorporation());
                Long marketPrice = domesticStockPriceList.get(index)*noDuplicateAssetList.get(k).getCount();
                Long myPrice = noDuplicateAssetList.get(k).getAveragePrice()*noDuplicateAssetList.get(k).getCount();
                principal+=myPrice;
                profit+=(marketPrice-myPrice);
            }
            else{
                continue;
            }
        }

        myAssetValue = principal+profit;
        d_percent = ((((double)principal/(double)myAssetValue))-1)*100;
        String percent = String.format("%.2f", d_percent);
        if(percent.charAt(0)=='-'){
            percent=percent.substring(1);
        }

        model.addAttribute("percent", percent);
        model.addAttribute("myAssetValue", myAssetValue);
        model.addAttribute("principal", principal);
        model.addAttribute("noDuplicateAssetList", noDuplicateAssetList);
        model.addAttribute("id",id);
        model.addAttribute("memberId", id);
        model.addAttribute("profit", profit);
        model.addAttribute("percent", percent);


        return "mypage";
    }


    @GetMapping("/register_asset")
    public String registerAssetView(Model model){

        final String domesticStockUrl = "https://kr.investing.com/equities/south-korea";
        Connection domesticStock = Jsoup.connect(domesticStockUrl);
        ArrayList<String> domesticStockList = new ArrayList<>();
        ArrayList<String> domesticStockInformationList = new ArrayList<>();

        try{
            Document domesticCurrency = domesticStock.get();
            Elements domesticCurrencyInformationList = domesticCurrency.select("#cross_rate_markets_stocks_1 td");
            for(Element company : domesticCurrencyInformationList){
                domesticStockInformationList.add(company.text());
            }
            for(int k=0;k<50;k++){
                domesticStockList.add(domesticStockInformationList.get(10*k+1));
            }
//            System.out.println(domesticStockList);
//            System.out.println(domesticStockPriceList);

        }catch(
                IOException e){
            e.printStackTrace();
        }
        model.addAttribute("stockList",domesticStockList);

        return "register_asset";
    }

    @RequestMapping(value="/register_enter", method={RequestMethod.POST})
    public String registerAsset(AssetForm assetForm){
        Asset asset = new Asset();
        asset.setId(assetForm.getId());
        asset.setKind(assetForm.getKind());
        asset.setCorporation(assetForm.getCorporation());
        asset.setCount(assetForm.getCount());
        asset.setAveragePrice(assetForm.getAveragePrice());
        asset.setAction(assetForm.getAction());
        memberService.register(asset);

//        System.out.println(asset.getId());
//        System.out.println(asset.getKind());
//        System.out.println(asset.getCount());
//        System.out.println(asset.getCorporation());

        return "continue_or_stop";
    }

    @GetMapping("/continue_or_stop")
    public String continueOrStop(){
        return "continue_or_stop";
    }

}
