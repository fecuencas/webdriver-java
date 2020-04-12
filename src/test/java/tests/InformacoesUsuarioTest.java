package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class InformacoesUsuarioTest {

    private WebDriver navegador;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp(){
        // Abrindo o Navegador
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\u6064810\\drivers\\chromedriver.exe");
        navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        navegador.manage().window().maximize();

        // Navegando para a pagina do Taskit!
        navegador.get("http://www.juliodelima.com.br/taskit");

        // Clicar no link que possue o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        // Identificando o formulario de Login
        WebElement formSignInBox = navegador.findElement(By.id("signinbox"));

        // Digitar no campo de name "login" que esta dentro do formulário de id "signinbox" o texto "julio0001"
        formSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        // Digitar no campo de name "password" que esta dentro do formulário de id "signinbox" o texto "123456"
        formSignInBox.findElement(By.name("password")).sendKeys("123456");

        // Clicar no link com o texto "SIGN IN"
        WebElement signInElement = navegador.findElement(By.id("signinbox"));
        signInElement.findElement(By.linkText("SIGN IN")).click();

        // Clicar em um link que possue o texto "me"
        //navegador.findElement(By.className("me")).click();
        navegador.findElement(By.xpath("//ul[@class=\"right hide-on-med-and-down\"]/li/a[@class=\"me\"]")).click();

        // Clicar em um link que possue o texto "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionaDoUsuario() {

        // Clicar no botão atraves do seu xpath
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        // Identificar o formulario de i addmoredata
        WebElement popUpAddMoreData = navegador.findElement(By.id("addmoredata"));

        // Na combo de name "type" escolher a opção Phone
        WebElement campoType = popUpAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText("Phone");

        // No campo de name "contact", digitar "+511999999999"
        popUpAddMoreData.findElement(By.name("contact")).sendKeys("+511999999999");

        // Clicar no link com o texto "SAVE"
        popUpAddMoreData.findElement(By.linkText("SAVE")).click();

        // Na mensagem de id "toast-container", validar que o Texto é "Your contact has been added!"
        WebElement mensagemPopUp = navegador.findElement(By.id("toast-container"));
        String mensagemPopUpText = mensagemPopUp.getText();
        assertEquals("Your contact has been added!", mensagemPopUpText);

    }

    @Test
    public void removerUmContatoDeUmUsuario() throws InterruptedException {
        // Clicar no elemento pelo seu xpath //spam[text()="+551133334444"]/following-sibiling::a

        /*List<WebElement> elements = navegador.findElements(By.xpath("//span[@class=\"title\"]"));
        System.out.println("Number of elements:" +elements.size());

        for (int i=0; i<elements.size();i++){

            if(("+511999999999").equalsIgnoreCase(elements.get(i).getText())) {
                System.out.println("Elemento selecionado: " + elements.get(i).getText());
                //span[@class="title"][text()="tes1@c5.com"]/following-sibling::a
                WebElement element = navegador.findElement(By.xpath("//span[@class=\"title\"][text()=\""+elements.get(i).getText()+"\"]/following-sibling::a"));
                element.findElement(By.xpath("//i[text()=\"delete\"]")).click();
                //navegador.findElement(By.xpath("//span[text()="+"\""+ elements.get(i).getText() +"\""+"]/following-sibling::a")).click();

            }
            else{
                System.out.println("Elemento descartado: " + elements.get(i).getText());

                JavascriptExecutor js = (JavascriptExecutor) navegador;
                js.executeScript("window.scrollBy(0,250)", "");
            }
        }*/

        navegador.findElement(By.xpath("//span[text()=\"+5511999999999\"]/following-sibling::a")).click();

        // Confirmar a janela javascript
        navegador.switchTo().alert().accept();
        String screenshotArquivo = "C:\\Users\\u6064810\\test-report\\" + Generator.dataHoraParaArquivo() + testName.getMethodName()+".png";
        Screenshot.tirar(navegador, screenshotArquivo);

        // Validar que a mensagem apresentada foi "Rest in peace, dear phone!"
        WebElement mensagemPopUp = navegador.findElement(By.id("toast-container"));
        String mensagemPopUpText = mensagemPopUp.getText();
        assertEquals("Rest in peace, dear phone!", mensagemPopUpText);

        // Aguardar até 10 segundos para a janela desaparecer
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPopUp));

        // Clicar no link com o texto "Logout"
        navegador.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown() {
        // Fechar navegador
        navegador.close();
    }
}
