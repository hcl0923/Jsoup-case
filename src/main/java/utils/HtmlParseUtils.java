package utils;

import bean.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Jsoup
 * @description:只能一两百行jd的html
 * @author: 作者
 * @create: 2021-04-30 17:48
 */
public class HtmlParseUtils {
    public static void main(String[] args) throws Exception {
        new HtmlParseUtils().pareJD("java").forEach(System.out::println);
    }

    public List<Content> pareJD(String keywords) throws Exception {
        //获取请求  https://search.jd.com/Search?keyword=java
        //前提联网，ajax不能获取到

        String url = "https://search.jd.com/Search?keyword=" + keywords;
        //解析网页.(Jsoup返回Document就是Document对象)
        Document document = Jsoup.parse(new URL(url), 30000);
        //所有你在js中使用的方法这里都能用！
        Element element = document.getElementById("J_goodsList");
        //获取所有的li元素
        Elements elements = element.getElementsByTag("li");
        ArrayList<Content> goodsList = new ArrayList<>();


        //获取元素中的内容，这里el就是每一个li标签了
        for (Element el : elements) {
            //关于这种图片特别多的网站，所有的图片都是延时加载的！
            //source-data-lazy-img
            String img = el.getElementsByTag("image").eq(0).attr("source-data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();

            Content content = new Content();
            content.setTitle(title);
            content.setPrice(price);
            content.setImg(img);
            goodsList.add(content);
        }
        return goodsList;
    }
}
