package br.com.getnetApi;

import static io.restassured.RestAssured.given;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReqresTest {
	final String URL = "https://reqres.in/api/";
	Response response;
	
	@Test
	public void createErro() {
		given().when().post(URL + "users").then().statusCode(415);

	}

	@Test
	public void createSucess() {
		String name = "Gilmar Rodrigues";
		String job = "Analista de Testes";
		response = given().body("{'name': '"+name+"','job': '"+job+"'}")
							.when().post(URL + "users");
		response.then().statusCode(201);

	}
	
	@Test
	public void singleUserErro()
	{
		String idUserConsulta = "999";
		given()
		.when()
			.get(URL+"users/"+idUserConsulta)
		.then().statusCode(404);
	}

	
	@Test
	public void singleUserSucess()
	{
		String idUserConsulta = "9";
		String email = "tobias.funke@reqres.in";
		String frist_Name = "Tobias";
		String last_Name = "Funke";
		String company = "StatusCode Weekly";
		response = given().when().get(URL+"users/"+idUserConsulta);
		response.jsonPath().getString("data.id").equals(idUserConsulta);
		response.jsonPath().getString("data.email").equals(email);
		response.jsonPath().getString("data.first_name").equals(frist_Name);
		response.jsonPath().getString("data.last_name").equals(last_Name);
		response.jsonPath().getString("ad.company").equals(company);
	}
	
	@Test
	public void registerErro()
	{
		
			String email = "gilmar@reqres.in";
			response = given().body("{'email': '"+email+"'}")
								.when().post(URL + "register");
			response.then().statusCode(400);
			response.jsonPath().getString("error").equals("Missing password");

		
	}
	@Test
	public void registerSucess() {
			String email = "eve.holt@reqres.in";
			String passWord = "pistol";
		    String id = "4";
		    String token =  "QpwL5tke4Pnpja7X4";
		response = given()
					.header("Content-Type", "application/json;charset=utf-8")
					.header("Host", "reqres.in")
					.body("{\"email\": \""+email+"\",\"password\": \""+passWord+"\"}")
				.when()
					.post(URL + "register");
		response.then().statusCode(200);
		response.jsonPath().getString("id").equals(id);
		response.jsonPath().getString("token").equals(token);

	}

}
