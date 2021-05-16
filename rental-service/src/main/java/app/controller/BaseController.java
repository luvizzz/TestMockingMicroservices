package app.controller;

import app.utils.ErrorMessage;
import app.utils.JsonProperty;
import app.utils.Utils;
import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Logger;

import static org.joda.time.format.ISODateTimeFormat.dateTime;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public abstract class BaseController {
    private static final Logger LOG = Logger.getLogger(BaseController.class.getSimpleName());
    protected String path = "/";

    protected static final ErrorMessage ENTITY_DOES_NOT_EXIST_MSG = new ErrorMessage(NOT_FOUND, "Entity does not exist");
    protected static final ErrorMessage CUSTOMER_PROVIDED_IN_BODY_MSG = new ErrorMessage(BAD_REQUEST, "Please do not provide customer in body");
    protected static final ErrorMessage RENTAL_ID_PROVIDED_IN_BODY_MSG = new ErrorMessage(BAD_REQUEST, "Please do not provide rental id in body");
    protected static final ErrorMessage INVALID_DEPENDENCY_MSG = new ErrorMessage(BAD_REQUEST, "Please provide valid id for associated entity");

    protected ResponseEntity<String> createErrorResponse(ErrorMessage msg) {
        HttpStatus status = msg.getStatus();
        return ResponseEntity.status(status).body(
                Utils.toJson(List.of(
                        new JsonProperty("timestamp", LocalDateTime.now().toString(dateTime())),
                        new JsonProperty("status", status.value()),
                        new JsonProperty("error", status.getReasonPhrase()),
                        new JsonProperty("message", msg.getMessage()),
                        new JsonProperty("path", path)
                )));
    }
}
