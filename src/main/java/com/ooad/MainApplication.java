package com.ooad;

import javafx.application.Application;
import javafx.stage.Stage;

import com.ooad.Forms.ApprovalForm;
import com.ooad.Forms.HomepageForm;
import com.ooad.Forms.LoginForm;
import com.ooad.Forms.OfferForm;
import com.ooad.Forms.RegisterForm;
import com.ooad.Forms.SellerHomepageForm;
import com.ooad.Forms.TransactionHistory;
import com.ooad.Forms.UploadForm;
import com.ooad.Forms.WishlistForm;

public class MainApplication extends Application {

    private Stage primaryStage;
    public UserSession userSession;


    @Override
    public void start(Stage primaryStage) {
        userSession = UserSession.getInstance();
        this.primaryStage = primaryStage;  
        showLoginPage();
    }

    public void showLoginPage() {
        LoginForm loginForm = new LoginForm(this);
        loginForm.start(primaryStage);
    }

    public void showRegisterPage() {
        RegisterForm registerForm = new RegisterForm(this);
        registerForm.start(primaryStage);
    }

    public void showUploadPage() {
        UploadForm uploadForm = new UploadForm(this);
        uploadForm.start(primaryStage);
    }

    public void showOfferPage() {
        OfferForm offerForm = new OfferForm(this);
        offerForm.start(primaryStage);
    }

    public void showHomePage() {
        System.out.println("SHOW HOMEPAGE");
        HomepageForm homepageForm = new HomepageForm(this);
        homepageForm.start(primaryStage);
    }

    public void showApprovalPage() {
        ApprovalForm approvalForm = new ApprovalForm(this);
        approvalForm.start(primaryStage);
    }

    public void showSellerHomepage() {
        SellerHomepageForm sellerHomepageForm = new SellerHomepageForm(this);
        sellerHomepageForm.start(primaryStage);
    }

    public void showWishlistPage() {
        WishlistForm wishlistForm = new WishlistForm(this);
        wishlistForm.start(primaryStage);
    }

    public void showTransactionPage() {
        TransactionHistory transactionHistory = new TransactionHistory(this);
        transactionHistory.start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}
