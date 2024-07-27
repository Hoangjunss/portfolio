package com.baconbao.portfolio.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum Error {
    //Client Error
    NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND), //Resource not found
    BAD_REQUEST(400, "Bad request", HttpStatus.BAD_REQUEST), //Syntax error or malformed request
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED), // unauthenticated user
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN), //The user does not have permission to access the resource
    CONFLICT(409, "Conflict", HttpStatus.CONFLICT), // Resource state conflicts. For example, it can happen when trying to create a duplicate record or update data that is being edited at the same time by someone else.
    //Server Error
    UNCATEGORIZED_EXCEPTION(9999, "Unclassified error", HttpStatus.INTERNAL_SERVER_ERROR),
    //Database Error
    DATABASE_ACCESS_ERROR(9998, "Database access error", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_KEY(9996, "Duplicate key found", HttpStatus.CONFLICT),
    EMPTY_RESULT(9995, "No result found", HttpStatus.NOT_FOUND),
    NON_UNIQUE_RESULT(9994, "Non-unique result found", HttpStatus.CONFLICT),
    //User-related errors
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1002, "User already exists", HttpStatus.CONFLICT),
    USER_UNABLE_TO_SAVE(1003, "Unable to save user", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UNABLE_TO_UPDATE(1004, "Unable to update user", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UNABLE_TO_DELETE(1005, "Unable to delete user", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EMAIL_ALREADY_EXISTS(1006, "Email user already exists", HttpStatus.BAD_REQUEST),
    //Profile-related errors
    PROFILE_NOT_FOUND(2001, "Profile not found", HttpStatus.NOT_FOUND),
    PROFILE_ALREADY_EXISTS(2002, "Profile already exists", HttpStatus.CONFLICT),
    PROFILE_UNABLE_TO_SAVE(2003, "Unable to save profile", HttpStatus.INTERNAL_SERVER_ERROR),
    PROFILE_UNABLE_TO_UPDATE(2004, "Unable to update profile", HttpStatus.INTERNAL_SERVER_ERROR),
    PROFILE_UNABLE_TO_DELETE(2005, "Unable to delete profile", HttpStatus.INTERNAL_SERVER_ERROR),
    //Project-related errors
    PROJECT_NOT_FOUND(3001, "Project not found", HttpStatus.NOT_FOUND),
    PROJECT_ALREADY_EXISTS(3002, "Project already exists", HttpStatus.CONFLICT),
    PROJECT_UNABLE_TO_SAVE(3003, "Unable to save project", HttpStatus.INTERNAL_SERVER_ERROR),
    PROJECT_UNABLE_TO_UPDATE(3004, "Unable to update project", HttpStatus.INTERNAL_SERVER_ERROR),
    PROJECT_UNABLE_TO_DELETE(3005, "Unable to delete project", HttpStatus.INTERNAL_SERVER_ERROR),
    //Notification-related errors
    NOTIFICATION_NOT_FOUND(4001, "Notification not found", HttpStatus.NOT_FOUND),
    NOTIFICATION_ALREADY_EXISTS(4002, "Notification already exists", HttpStatus.CONFLICT),
    NOTIFICATION_UNABLE_TO_SAVE(4003, "Unable to save notification", HttpStatus.INTERNAL_SERVER_ERROR),
    NOTIFICATION_UNABLE_TO_UPDATE(4004, "Unable to update notification", HttpStatus.INTERNAL_SERVER_ERROR),
    NOTIFICATION_UNABLE_TO_DELETE(4005, "Unable to delete notification", HttpStatus.INTERNAL_SERVER_ERROR),
    //Comments-related errors
    COMMENT_NOT_FOUND(5001, "Comment not found", HttpStatus.NOT_FOUND),
    COMMENT_ALREADY_EXISTS(5002, "Comment already exists", HttpStatus.CONFLICT),
    COMMENT_UNABLE_TO_SAVE(5003, "Unable to save comment", HttpStatus.INTERNAL_SERVER_ERROR),
    COMMENT_UNABLE_TO_UPDATE(5004, "Unable to update comment", HttpStatus.INTERNAL_SERVER_ERROR),
    COMMENT_UNABLE_TO_DELETE(5005, "Unable to delete comment", HttpStatus.INTERNAL_SERVER_ERROR),
    //Contact-related errors
    CONTACT_NOT_FOUND(6001, "Contact not found", HttpStatus.NOT_FOUND),
    CONTACT_ALREADY_EXISTS(6002, "Contact already exists", HttpStatus.CONFLICT),
    CONTACT_UNABLE_TO_SAVE(6003, "Unable to save contact", HttpStatus.INTERNAL_SERVER_ERROR),
    CONTACT_UNABLE_TO_UPDATE(6004, "Unable to update contact", HttpStatus.INTERNAL_SERVER_ERROR),
    CONTACT_UNABLE_TO_DELETE(6005, "Unable to delete contact", HttpStatus.INTERNAL_SERVER_ERROR),
    //Image-related errors
    IMAGE_NOT_FOUND(7001, "Image not found", HttpStatus.NOT_FOUND),
    IMAGE_ALREADY_EXISTS(7002, "Image already exists", HttpStatus.CONFLICT),
    IMAGE_UNABLE_TO_SAVE(7003, "Unable to save image", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_UNABLE_TO_UPDATE(7004, "Unable to update image", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_UNABLE_TO_DELETE(7005, "Unable to delete image", HttpStatus.INTERNAL_SERVER_ERROR),
    //AuthenticationRequest-related exceptions
    AUTHENTICATION_REQUEST_INVALID(8001, "Invalid authentication request", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_REQUEST_UNSUPPORTED(8002, "Unsupported authentication request", HttpStatus.BAD_REQUEST),
    //AuthenticationResponse-related exceptions
    AUTHENTICATION_RESPONSE_FAILED(9001, "Failed to generate authentication response", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTHENTICATION_TOKEN_EXPIRED(9002, "Authentication token expired", HttpStatus.UNAUTHORIZED),
    // Cloudinary-related errors
    UPLOAD_FAILED(10001, "Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_FAILED(10002, "Failed to delete file", HttpStatus.INTERNAL_SERVER_ERROR),
    CONVERSION_FAILED(10003, "Failed to convert file", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_DELETION_FAILED(10004, "Failed to delete temporary file", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    /**
     * Constructor for ErrorCode.
     *
     * @param code       the error code
     * @param message    the error message
     * @param statusCode the corresponding HTTP status code
     */
    Error(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
