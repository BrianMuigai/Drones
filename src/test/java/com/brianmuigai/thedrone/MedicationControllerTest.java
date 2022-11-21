package com.brianmuigai.thedrone;

import com.brianmuigai.thedrone.entities.Medication;
import com.brianmuigai.thedrone.responses.ListResponseWrapper;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
public class MedicationControllerTest extends BaseTestClass {

    Medication medication = new Medication(
            "CP201",
            "TestMedication1",
            20
    );

    @Test
    public void getMedications() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/get-medications")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
        String content = mvcResult.getResponse().getContentAsString();
        ListResponseWrapper medication = super.mapFromJson(content, ListResponseWrapper.class);
        assertThat(medication._embedded.medicationList.size()).isGreaterThan(0);
    }

    @Test
    public void createMedication() throws Exception {
        String inputJson = super.mapToJson(medication);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/register-medication")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(super.mapFromJson(content, Medication.class), medication);
    }

    @Test
    @Order(3)
    public void createExistingMedication() throws Exception {
        String inputJson = super.mapToJson(medication);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/register-medication")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
    }

    @Test
    @Order(4)
    public void getDrone() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/get-medication/CP201")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
        String content = mvcResult.getResponse().getContentAsString();
        Medication med = super.mapFromJson(content, Medication.class);
        assertEquals(med.getCode(), "CP201");
    }
}
