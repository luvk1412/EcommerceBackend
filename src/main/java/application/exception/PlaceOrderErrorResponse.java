package application.exception;

import java.util.List;

public class PlaceOrderErrorResponse {
    List<InsufficientProduct> insufficientProducts;

    public PlaceOrderErrorResponse(List<InsufficientProduct> insufficientProducts) {
        this.insufficientProducts = insufficientProducts;
    }

    public List<InsufficientProduct> getInsufficientProducts() {
        return insufficientProducts;
    }

    public void setInsufficientProducts(List<InsufficientProduct> insufficientProducts) {
        this.insufficientProducts = insufficientProducts;
    }
}
