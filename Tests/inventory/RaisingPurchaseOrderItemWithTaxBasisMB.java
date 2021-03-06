package inventory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import GenericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Login;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.ItemVerification;
import reusableFunctions.inventory.PackageUOM;
import reusableFunctions.inventory.PurchaseOrder;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class RaisingPurchaseOrderItemWithTaxBasisMB {

WebDriver driver = null;
KeywordSelectionLibrary executeStep;
KeywordExecutionLibrary initDriver;
VerificationFunctions verifications;
ReportingFunctions reporter;
String AutomationID;
String DataSet;
String testCaseStatus="FAIL";

//@BeforeSuite
//public void BeforeSuite(){
  private void openBrowser(){
	System.out.println("BeforeSuite");
	AutomationID = "Login - RaisingPurchaseOrderItemWithTaxBasisMB";
	reporter = new ReportingFunctions();
	System.out.println("AfterReport");
	
	initDriver = new KeywordExecutionLibrary();
	System.out.println("AfterInitDriver");

	try{
		System.out.println("EnvironmentSetUp is ::" + EnvironmentSetup.URLforExec);
		driver = initDriver.LaunchApp("Chrome",  EnvironmentSetup.URLforExec);
		System.out.println("Am here");
	}catch(Exception e){
		e.printStackTrace();
	}
	Assert.assertFalse(driver==null, "Browser Not Initialized - Check Log File for Errors");
	initDriver = null;
	verifications = new VerificationFunctions(driver, AutomationID, DataSet);
	verifications.verify(SeleniumVerifications.Appears, "", "LoginScreenHospital_Est", false);
	verifications = null;
	
}

 //@BeforeMethod
//public void BeforeTest(){
  private void login(){
	AutomationID = "Login - RaisingPurchaseOrderItemWithTaxBasisMB";
	DataSet = "PreReq_005";
			
	executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
	verifications = new VerificationFunctions(driver, AutomationID, DataSet);
	
	Login OPWorkFlowLogin = new Login(executeStep,verifications);
	OPWorkFlowLogin.login();

	executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
	System.out.println("After Close Tour");
	CommonUtilities.delay();
	executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
	System.out.println("After Close Tour");
	
}
  
  @BeforeClass
	public void RaisingPurchaseOrderItemWithTaxBasisMBPreRequisite(){
		openBrowser();
		login();
		DataSet = "PreReq_005";
		AutomationID = "RaisingPurchaseOrderItemWithTaxBasisMBPreRequisite";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test RaisingPurchaseOrderItemWithTaxBasisMB - Before Navigation");
		
	    executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
		//Click on Add New Categor * Enter Category Name * Select Identification = Batch No * Issue Type = Consumable -> Click on save button.
		//2. -> Create a serial Category in Item Category Master under stores master
		
		navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
		ItemCategories itemCat = new ItemCategories(executeStep, verifications);
		itemCat.AddItemcategory("Itemcategory", false);
		
		//Package UOM --> Click on Add Package UOM --> Enter Package UOM,Unit UOM and Pkg Size = 10
		navigation.navigateToSettings(driver, "PackageUOM", "PackageUOMHeader");
		PackageUOM pkgUOM = new PackageUOM(executeStep, verifications);
		pkgUOM.AddPackageUOMItem("pakageUOMItem");
		
		//Create items in Item Master -->store master-> Click on add new item
		//Enter Item Name say item1, Shorter Name, Manufacturer.
		//Select Category say Batch No/Serial NO, Service Sub Group = general,
		//Tax Basis = MRP Based(with bonus) and Tax(%) = 5.5
		
		navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
		ItemMaster itemMaster =new ItemMaster(executeStep, verifications);
		itemMaster.AddItem("ItemMaster");
		
		//6. In Supplier Masters, add 'Supplier_1' with Credit Period and Address --In Supplier Masters.
		
		navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
		executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
		    
		SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
		supplier.createSupplier();
		
		  }
  
  @Test()
	public void RaisingPurchaseOrderItemWithTaxBasisMBTest(){
	  
		DataSet = "TS_005";
		AutomationID = "RaisingPurchaseOrderItemWithTaxBasisMBTest";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test RaisingPurchaseOrderItemWithTaxBasisMBTest - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
	
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "PurchaseOrderLink", "PurchaseOrderHeader");
		
		PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
		purchaseOrder.raisePurchaseOrder();
		purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
		purchaseOrder.Save();
		
		//4. Ensure that item is added in the grid.
		ItemVerification itmVer = new ItemVerification(executeStep, verifications);
		itmVer.ItemsVerification("PurchaseItem");

		//Expected Result for Tax amount and Total
		PurchaseOrderTotalAndTax("PurchaseItem");	
		
  }
  
  
  public void PurchaseOrderTotalAndTax(String lineItemId){
		
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			EnvironmentSetup.intGlobalLineItemCount= 1;
			verifications.verify(SeleniumVerifications.Appears, "Total","PurchaseOrderItemWithMBTaxBasisTotal",false);
			verifications.verify(SeleniumVerifications.Appears, "TaxAmt","PurchaseOrderItemWithMBTaxBasisTaxAmt",false);
	
			EnvironmentSetup.lineItemCount++;
			EnvironmentSetup.intGlobalLineItemCount++;
		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.intGlobalLineItemCount = 0;
		EnvironmentSetup.UseLineItem = false;
		

  }	
  
  @AfterTest
	public void closeBrowser(){
  	reporter.UpdateTestCaseReport(AutomationID, DataSet, " InventoryPurchaseOrderWithMRPBasedBonus workflow", null, "", "", testCaseStatus);
		driver.close();
	}
}
