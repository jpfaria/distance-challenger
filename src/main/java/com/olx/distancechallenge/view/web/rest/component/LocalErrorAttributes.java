package com.olx.distancechallenge.view.web.rest.component;

import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class LocalErrorAttributes extends DefaultErrorAttributes
        implements ErrorAttributes, HandlerExceptionResolver, Ordered {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<String, Object>();
        errorAttributes.put("timestamp", new Date());
        addErrorDetails(errorAttributes, webRequest, includeStackTrace);
        //addStatus(errorAttributes, requestAttributes);
        //addPath(errorAttributes, requestAttributes);
        return errorAttributes;
    }


    private void addErrorDetails(Map<String, Object> errorAttributes,
                                 WebRequest webRequest, boolean includeStackTrace) {
        Throwable error = getError(webRequest);
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = ((ServletException) error).getCause();
            }
            errorAttributes.put("exception", error.getClass().getName());
            addErrorMessage(errorAttributes, error);
            if (includeStackTrace) {
                addStackTrace(errorAttributes, error);
            }
            return;
        }
        Object message = getAttribute(webRequest, "javax.servlet.error.message");
        if ((!StringUtils.isEmpty(message) || errorAttributes.get("message") == null)
                && !(error instanceof BindingResult)) {
            errorAttributes.put("message",
                    StringUtils.isEmpty(message) ? "No message available" : message);
        }

    }

    private void addErrorMessage(Map<String, Object> errorAttributes, Throwable error) {
        BindingResult result = extractBindingResult(error);
        if (result == null) {
            errorAttributes.put("message", error.getMessage());
            return;
        }
        if (result.getErrorCount() > 0) {
            errorAttributes.put("errors", result.getAllErrors());
            errorAttributes.put("message",
                    "Validation failed for object='" + result.getObjectName()
                            + "'. Error count: " + result.getErrorCount());
        }
        else {
            errorAttributes.put("message", "No errors");
        }
    }

    private BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return (BindingResult) error;
        }
        if (error instanceof MethodArgumentNotValidException) {
            return ((MethodArgumentNotValidException) error).getBindingResult();
        }
        return null;
    }

    private void addStackTrace(Map<String, Object> errorAttributes, Throwable error) {
        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        errorAttributes.put("trace", stackTrace.toString());
    }

    private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
