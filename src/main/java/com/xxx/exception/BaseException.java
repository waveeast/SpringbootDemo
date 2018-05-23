package com.xxx.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fu.dongbo on 2018/5/23.
 * Description:
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 370628118030897564L;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseException.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String FIELD_NAME_ERROR_CAUSE = "errorCause";


    private Map<String, String> errorDesc;
    private ErrorConstants.Errors error;

    public BaseException(Throwable throwable, ErrorConstants.Errors error, Map<String, String> errorDesc) {
        super(throwable);
        this.error = error;
        this.errorDesc = errorDesc;
    }

    public Map<String, String> getErrorDesc() {
        return errorDesc;
    }

    public ErrorConstants.Errors getError() {
        return error;
    }

    @Override
    public String getMessage() {
        Throwable cause = getCause();
        Map<String, Object> map = new HashMap<>();
        map.put(this.error.name(), errorDesc);
        map.put(FIELD_NAME_ERROR_CAUSE, cause);
        String fullMsg = null;
        try {
            fullMsg = OBJECT_MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            LOGGER.error("serialization errors failed", e);
            throw new RuntimeException(e);
        }
        return fullMsg;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        BaseException targetBaseException = (BaseException) obj;
        if (!this.error.equals(targetBaseException.getError())) {
            return false;
        }

        Throwable currentCause = this.getCause();
        Throwable targetBaseExceptionCause = targetBaseException.getCause();
        if (null == currentCause) {
            if (null != targetBaseExceptionCause) {
                return false;
            }
        } else {
            if (!currentCause.equals(targetBaseExceptionCause)) {
                return false;
            }
        }

        return this.errorDesc.equals(targetBaseException.getErrorDesc());
    }
}