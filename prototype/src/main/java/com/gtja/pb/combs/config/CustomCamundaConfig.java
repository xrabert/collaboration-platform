package com.gtja.pb.combs.config;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.identity.impl.ldap.plugin.LdapIdentityProviderPlugin;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Configuration
public class CustomCamundaConfig {

  @Bean
  @Order(Ordering.DEFAULT_ORDER + 1)
  public static ProcessEnginePlugin strongUUIDGenerator() {
    return new ProcessEnginePlugin() {
      @Override
      public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setIdGenerator(new StrongUuidGenerator());
      }

      @Override
      public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
      }

      @Override
      public void postProcessEngineBuild(ProcessEngine processEngine) {
      }
    };
  }

  @Bean
  @Order(Ordering.DEFAULT_ORDER + 2)
  public static LdapIdentityProviderPlugin ldapIdentityProviderPlugin() {

    LdapIdentityProviderPlugin plugin = new LdapIdentityProviderPlugin();

    //TODO: using properties file for ldap information
    plugin.setServerUrl("ldap://localhost:8389/dc=pb,dc=gtja,dc=com");
    plugin.setAcceptUntrustedCertificates(false);
    plugin.setAllowAnonymousLogin(false);

    //TODO: using ssl in future
    plugin.setUseSsl(false);
    plugin.setSecurityAuthentication("simple");

    // manager Einstellungen
    //plugin.setBaseDn("DC=kueche,DC=kochtopf,DC=de");
    //plugin.setManagerDn("CN=Weißwurscht,OU=Kochtopf,OU=Kueche,OU=Erdgeschoß,DC=Haus,DC=Dahoam,DC=de");
    //plugin.setManagerPassword("MitVielSenfUndBittschönNurDasOriginal");

    // user-spezifische Einstellungen
    //plugin.setUserSearchBase("ou=HungrigeLeid");
    //plugin.setUserDnPattern("ou=staffs");
    plugin.setUserSearchFilter("(objectclass=inetOrgPerson)");
    //plugin.setUserIdAttribute("uid");
    //plugin.setUserFirstnameAttribute("sn");
    //plugin.setUserLastnameAttribute("cn");
    //plugin.setUserEmailAttribute("schicksDoHi");

    // gruppen-spezifische Einstellungen
    plugin.setGroupSearchBase("ou=it");
    plugin.setGroupSearchFilter("(objectclass=groupOfUniqueNames)");
    //plugin.setGroupIdAttribute("cn");
    //plugin.setGroupNameAttribute("soHoasMa");
    plugin.setGroupMemberAttribute("uniqueMember");

    return plugin;
  }

  @Bean
  @Order(Ordering.DEFAULT_ORDER + 3)
  public static AdministratorAuthorizationPlugin administratorAuthorizationPlugin() {
    AdministratorAuthorizationPlugin plugin = new AdministratorAuthorizationPlugin();

    //plugin.setAdministratorGroupName("xut"); //Group Name available in the ldap server
    plugin.setAdministratorUserName("xut"); //User-id available in the ldap server

    return plugin;
  }
}
