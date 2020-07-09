package com.henlf.mail.callback;

public interface MailCallback {
    void onSuccess();

    void onError(Throwable cause);
}
