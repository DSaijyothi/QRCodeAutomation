package QRCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.Luminance;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeSuite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class QRCodeAutomation
{
    public WebDriver driver;
    @Test
    public void LaunchBrowser() throws InterruptedException, IOException, NotFoundException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(co);
        driver.get("http://qrcode.meetheed.com/qrcode_examples.php");
        driver.manage().window().maximize();
        String QRImage=driver.findElement(By.cssSelector("body > div.content > div > div > div.col-md-8.content-main > div.single-grid > div.ThreeRowGridItemCont > div:nth-child(1) > div.topBox > img")).getAttribute("src");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        URL url=new URL(QRImage);
        ImageIO.read(url);
        BufferedImage bufferedImage=ImageIO.read(url);
        //Luminance source is a interface which is used to get or code information
        LuminanceSource luminanceSource=new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(luminanceSource));
        Result result=new MultiFormatReader().decode(binaryBitmap);
        String TextInOrCode= result.getText();
        System.out.println("The Text in ORCode is:"+TextInOrCode);
        driver.quit();

    }
}
