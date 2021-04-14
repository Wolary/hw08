package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/drv.prop"})
public interface DrvCon extends Config {
    @Config.Key("remote.web.user")
    String remoteUser();

    @Key("remote.web.password")
    String remotePassword();
}