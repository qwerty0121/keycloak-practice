package controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.keycloak.KeycloakSecurityContext;

@Path("public")
public class PublicController {

	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello!!";
	}

	@GET
	@Path("keycloak-context")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> keycloakContext(@Context HttpServletRequest request) {
		Map<String, Object> results = new LinkedHashMap<>();

		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			results.put("principal-type", principal.getClass().getName());
			results.put("principal-name", principal.getName());
		}

		KeycloakSecurityContext context = (KeycloakSecurityContext) request
				.getAttribute(KeycloakSecurityContext.class.getName());
		if (context != null) {
			results.put("id-token-from-request", context.getIdToken());
		}

		KeycloakSecurityContext contextFromSession = (KeycloakSecurityContext) request.getSession()
				.getAttribute(KeycloakSecurityContext.class.getName());
		if (contextFromSession != null) {
			results.put("id-token-from-session", contextFromSession.getIdToken());
		}

		return results;
	}

}
