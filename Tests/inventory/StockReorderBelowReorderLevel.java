package inventory;

import java.util.List;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
import reusableFunctions.Order;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.Department;
import reusableFunctions.inventory.GRNModule;
import reusableFunctions.inventory.GenericPreferences;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.MiscellaneousSettings;
import reusableFunctions.inventory.PackageUOM;
import reusableFunctions.inventory.PaymentTerms;
import reusableFunctions.inventory.PurchaseOrder;
import reusableFunctions.inventory.RolesAndUsers;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.StockReorder;
import reusableFunctions.inventory.Stores;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class StockReorderBelowReorderLevel {
	

	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	
	//@BeforeSuite
		//public void BeforeSuite(){
		  private void openBrowser(){
			System.out.println("BeforeSuite");
			AutomationID = "Login - ViewGRN";
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
		AutomationID = "Login - StockReorderBelowReorderLevel";
		DataSet = "PreReq_46";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		//executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	  
	  @BeforeClass
		 public void StockReorderBelowReorderLevelPreRequisite(){
				openBrowser();
				login();
				DataSet = "PreReq_46";
				AutomationID = "StockReorderBelowReorderLevelPreRequisite";
				List<String> lineItemIDs = null;
				System.out.println("Inside Test StockReorderBelowReorderLevelPreRequisite - Before Navigation");
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			
			//1. Create Stores -> Stores Masters say st1
			
			navigation.navigateToSettings(driver, "StoresLink", "AddNewStoreLink");
			Stores store = new Stores(executeStep, verifications);
			store.createstore();
			
			//2. Create departments -> Hospital Admin Masters
			
			navigation.navigateToSettings(driver, "DepartmentsLink", "AddNewDepartmentLink");
			Department dept = new Department(executeStep, verifications);
			dept.AddDepartment();
			
			
			/*3. -> Create a batch Item Category in Item Category Master under stores master 
			Click on Add New Category
			  * Enter Category Name        
			  * Select Identification = Batch No
			  * Issue Type = Consumable */
			/*4. -> Create a serial Category in Item Category Master under stores master
			  Click on Add New Category
			  * Enter Category Name        
			  * Select Identification = Serial NO
			  * Issue Type = Consumable */
			
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
	        itemCat.AddItemcategory("Itemcategory", false);   
			
			
			//5. Create items in Item Master -->store master-> Click on add new item
			//6. Enter Item Name say item1, Shorter Name, Manufacturer.
			//7. Select Category say Batch No/Serial NO, Service Sub Group = general, 
			//Tax Basis = CP Based(without bonus) and Tax(%) = 5.5, 
	        
	      //8. In Store Wise List click on plus(+) button select the store enter Danger Level<Min Level<Reorder Level<Max Level (1000,2000,3000,4000)
	        
	        navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
			ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster"); 
	        
	        
		//9. In Supplier Masters, add 'Supplier_1' with Credit Period and Address
			 //In Supplier Masters."
			
			
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");
			SupplierDetails supplier = new SupplierDetails(executeStep, verifications);
			supplier.addSupplier();
			supplier.createMultipleSupplier("SupplierMaster");
			
	  }		
 
	
	

	 @Test(priority=1)
	     public void BelowReorderLevel(){
			  openBrowser();
			  login();
			  DataSet = "TS_046";
			  AutomationID = "Below Reorder Level";
			  List<String> lineItemIDs = null;
			  System.out.println("Inside Test Below Reorder Level - Before Navigation");
			  
			  executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
				
				SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
				navigation.navigateTo(driver, "StockReorderLink", "StockReorderPage");
				//StockReorder stock = new StockReorder();
				//stock.belowReorder();
				
				executeStep.performAction(SeleniumActions.Click, "","ReorderLevelRadioButton");
				verifications.verify(SeleniumVerifications.Appears, "","BelowReorderLevelRadioButton",true);
				
				executeStep.performAction(SeleniumActions.Click, "","BelowReorderLevelRadioButton");
				verifications.verify(SeleniumVerifications.Appears, "","ExcludeItemsCheckbox",true);
				
				executeStep.performAction(SeleniumActions.Click, "","ExcludeItemsCheckbox");
				verifications.verify(SeleniumVerifications.Appears, "","StockReorderPageSearchbutton",true);
				
				executeStep.performAction(SeleniumActions.Click, "","StockReorderPageSearchbutton");
				verifications.verify(SeleniumVerifications.Appears, "","StockReorderPage",true);
				
			 
			 
				
				}  

	  @Test(priority=2)
	     public void StockReoderBelowDangerLevel(){
			  openBrowser();
			  login();
			  DataSet = "TS_047";
			  AutomationID = "StockReoderBelowDangerLevel";
			  List<String> lineItemIDs = null;
			  System.out.println("Inside Test Below Reorder Level - Before Navigation");
			  
			  executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
				
				SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
				navigation.navigateTo(driver, "StockReorderLink", "StockReorderPage");
				//StockReorder stock = new StockReorder();
				//stock.belowReorder();
				
				executeStep.performAction(SeleniumActions.Click, "","ReorderLevelRadioButton");
				verifications.verify(SeleniumVerifications.Appears, "","BelowDangerLevelRadioButton",true);
				
				executeStep.performAction(SeleniumActions.Click, "","BelowDangerLevelRadioButton");
				verifications.verify(SeleniumVerifications.Appears, "","ExcludeItemsCheckbox",true);
				
				executeStep.performAction(SeleniumActions.Click, "","ExcludeItemsCheckbox");
				verifications.verify(SeleniumVerifications.Appears, "","StockReorderPageSearchbutton",true);
				
				executeStep.performAction(SeleniumActions.Click, "","StockReorderPageSearchbutton");
				verifications.verify(SeleniumVerifications.Appears, "","StockReorderPage",true);
				
			 
				}  
				}  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	
	

	