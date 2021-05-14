package net.okhotnikov.websocket.model.out;


/**
 * Created by Ognerezov on 02/12/16.
 */

public class BasicResponse {
    protected String value;

    protected String details;
    public static final String OK="ok";
    public static final String FAIL="fail";
    public static final String ACCESS_DENIED="access denied";
    public static final String INVALID_PLAYER="user unknown";
    public static final String LICENSE_EXPIRED="license expired";
    public static final String HARDWARE_LIMIT_VIOLATION="hardware limit violation";

    public BasicResponse() {
    }

    public BasicResponse(String value, String details) {
        this.value = value;
        this.details = details;
    }

    public static BasicResponse getOk(){
        return new BasicResponse(OK,null);
    }

    public static BasicResponse getFail(){
        return new BasicResponse(FAIL,null);
    }

    public static BasicResponse getFail(String reason){
        return new BasicResponse(FAIL,reason);
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
