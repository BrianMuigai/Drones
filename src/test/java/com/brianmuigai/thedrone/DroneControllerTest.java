package com.brianmuigai.thedrone;

import com.brianmuigai.thedrone.entities.Drone;
import com.brianmuigai.thedrone.enums.DroneModel;
import com.brianmuigai.thedrone.enums.DroneState;
import com.brianmuigai.thedrone.responses.ListResponseWrapper;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(1)
public class DroneControllerTest extends BaseTestClass {

    private final Drone drone = new Drone(
            "Bri254",
            DroneModel.Lightweight,
            50,
            65,
            DroneState.IDLE);

    @Test
    @Order(1)
    public void getDrones() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/get-drones")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
        String content = mvcResult.getResponse().getContentAsString();
        ListResponseWrapper drones = super.mapFromJson(content, ListResponseWrapper.class);
        assertThat(drones._embedded.droneList.size()).isGreaterThan(2);
    }

    @Test
    @Order(2)
    public void createDrone() throws Exception {
        String inputJson = super.mapToJson(drone);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/register-drone")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(super.mapFromJson(content, Drone.class), drone);
    }

    @Test
    @Order(3)
    public void createExistingDrone() throws Exception {
        String inputJson = super.mapToJson(drone);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/register-drone")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
    }

    @Test
    @Order(4)
    public void getDrone() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/get-drone/Bri254")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
        String content = mvcResult.getResponse().getContentAsString();
        Drone drone = super.mapFromJson(content, Drone.class);
        assertEquals(drone.getSerialNumber(), "Bri254");
    }
}
