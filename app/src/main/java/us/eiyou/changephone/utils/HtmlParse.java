package us.eiyou.changephone.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * Created by Au on 2015/10/18.
 */
public class HtmlParse {
    public static void hosts(File file) throws IOException {
        Document document = Jsoup.connect("https://github.com/racaljk/hosts/blob/master/hosts").get();
        String hosts = document.body().getElementsByTag("td").text().replace("  ", "\n");
        FileUtils.fileWirteWithAdd(file, hosts);
    }
}
