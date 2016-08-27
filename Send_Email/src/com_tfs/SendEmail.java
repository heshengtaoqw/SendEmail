package com_tfs;

import org.openqa.selenium.*;

import org.testng.annotations.Test;
import org.openqa.selenium.firefox.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;

public class SendEmail 
{
  WebDriver browser = new FirefoxDriver();
	
  @Parameters("url")
  @Test
  public void openHomePage(String url) throws Exception
  {
	  browser.get(url);
  }
 
  @Test(dependsOnMethods = "openHomePage" )
  public void openLoginPage() throws Exception
  {
	  WebElement loginButton = browser.findElement(By.linkText("��¼"));
	  loginButton.click();
  }
  
  @Parameters({"username","password"})
  @Test(dependsOnMethods ="openLoginPage")
  public void authentication(String username, String password) throws Exception
  {
	  WebElement userNameField = browser.findElement(By.id("inputuin"));
	  WebElement passwordField = browser.findElement(By.id("pp"));
	  WebElement checkbox = browser.findElement(By.id("ss"));
	  WebElement buttonLogin = browser.findElement(By.id("btlogin"));
	  checkbox.click();
	  userNameField.sendKeys(username);
	  passwordField.sendKeys(password);
	  buttonLogin.submit();
  }
  
  @AfterMethod
  public void pasue() throws Exception
  {
	  Thread.sleep(5000);
  }
  
  @Test(dependsOnMethods = "authentication")
  public void writeEmailPage() throws Exception
  {
	  WebElement writeButton = browser.findElement(By.linkText("д��"));
	  writeButton.click();
  }
  
  @Parameters({"send_To","subjects","context"})
  @Test(dependsOnMethods = "writeEmailPage")
  public void writeEmail(String send_To, String subjects, String context) throws Exception
  {
	  WebDriver mainFrame =  browser.switchTo().frame(("mainFrame"));
	  WebElement sendTo = mainFrame.findElement(By.xpath(".//*[@id='toAreaCtrl']/div[2]/input"));
	  WebElement subject = mainFrame.findElement(By.id("subject"));
	  sendTo.sendKeys(send_To);
	  subject.sendKeys(subjects);
	  WebDriver textFrame = mainFrame.switchTo().frame(mainFrame.findElement(By.className("QMEditorIfrmEditArea")));
	  textFrame.findElement(By.tagName("body")).sendKeys(context);
  }
  
  @Test(dependsOnMethods = "writeEmail")
  public void sendConfirm() throws Exception
  {
	  
	  browser.switchTo().defaultContent();
	  browser.switchTo().frame("mainFrame");
	  WebElement sendButton = browser.findElement(By.name("sendbtn"));
	  sendButton.click();
	  browser.switchTo().defaultContent();
	  Thread.sleep(3000);
	  WebElement confirmButton = browser.findElement(By.id("QMconfirm__confirm"));
	  confirmButton.click();
  }
  
  
  @AfterClass
  public void quit() throws Exception
  {
	  browser.quit();
  }
  
}
