package com.brianmuigai.thedrone;

import com.brianmuigai.thedrone.entities.Delivery;
import com.brianmuigai.thedrone.responses.BatteryStatusResponse;
import com.brianmuigai.thedrone.responses.ListResponseWrapper;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TheDispatchControllerTest extends BaseTestClass {
    @Test
    @Order(1)
    public void loadDrone() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/load-medication/CP201/Bri254")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
        String content = mvcResult.getResponse().getContentAsString();
        Delivery delivery =super.mapFromJson(content, Delivery.class);
        assertEquals("Bri254", delivery.getDrone().getSerialNumber());
    }

    @Test
    @Order(2)
    public void getDelivery() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/get-delivery/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);

        String content = mvcResult.getResponse().getContentAsString();
        Delivery delivery =super.mapFromJson(content, Delivery.class);
        assertEquals(1, delivery.getId());
    }

    @Test
    @Order(3)
    public void getDeliveries() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/get-deliveries")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
        String content = mvcResult.getResponse().getContentAsString();
        ListResponseWrapper drones = super.mapFromJson(content, ListResponseWrapper.class);
        assertThat(drones._embedded.deliveryList.size()).isEqualTo(1);
    }

    @Test
    public void checkLoadMedication() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/check-loaded-medications/Bri254")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
        String content = mvcResult.getResponse().getContentAsString();
        ListResponseWrapper drones = super.mapFromJson(content, ListResponseWrapper.class);
        assertThat(drones._embedded.medicationList.size()).isEqualTo(1);
    }

    @Test
    public void getAvailableDrones() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/get-available-drones/50")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);

        String content = mvcResult.getResponse().getContentAsString();
        ListResponseWrapper drones = super.mapFromJson(content, ListResponseWrapper.class);
        assertThat(drones._embedded.droneList.size()).isEqualTo(3);
    }

    @Test
    public void getAvailableDronesWrong() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/get-available-drones/0")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);

        String content = mvcResult.getResponse().getContentAsString();
        ListResponseWrapper drones = super.mapFromJson(content, ListResponseWrapper.class);
        assertThat(drones._embedded.droneList.size()).isEqualTo(4);
    }

    @Test
    public void checkBatteryLevel() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/check-battery-level/Bri254")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);

        String content = mvcResult.getResponse().getContentAsString();
        BatteryStatusResponse batteryStatusResponse = super.mapFromJson(content, BatteryStatusResponse.class);
        assertEquals(65, batteryStatusResponse.battery_capacity);
    }
}
