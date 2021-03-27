package com.perso.back.task_planner.registration;

import java.util.Locale;

import com.perso.back.task_planner.infra.dto.UserDBDto;
import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final UserDBDto userDBDto;

    public OnRegistrationCompleteEvent(final UserDBDto userDBDto, final Locale locale, final String appUrl) {
        super(userDBDto);
        this.userDBDto = userDBDto;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserDBDto getUserDBDto() {
        return userDBDto;
    }

}
