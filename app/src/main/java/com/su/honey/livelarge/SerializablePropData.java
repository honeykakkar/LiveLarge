package com.su.honey.livelarge;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "imageURLs",
        "prop_AvailDate",
        "prop_address",
        "prop_amenities",
        "prop_area",
        "prop_bed",
        "prop_city",
        "prop_description",
        "prop_furnished",
        "prop_name",
        "prop_owner",
        "prop_price",
        "prop_state",
        "prop_type"
})

public class SerializablePropData implements Serializable
{
        @JsonProperty("imageURLs")
        private String imageURLs;
        @JsonProperty("prop_AvailDate")
        private String prop_AvailDate;
        @JsonProperty("prop_address")
        private String prop_address;
        @JsonProperty("prop_amenities")
        private String prop_amenities;
        @JsonProperty("prop_area")
        private String prop_area;
        @JsonProperty("prop_bed")
        private String prop_bed;
        @JsonProperty("prop_city")
        private String prop_city;
        @JsonProperty("prop_description")
        private String prop_description;
        @JsonProperty("prop_furnished")
        private String prop_furnished;
        @JsonProperty("prop_name")
        private String prop_name;
        @JsonProperty("prop_owner")
        private String prop_owner;
        @JsonProperty("prop_price")
        private String prop_price;
        @JsonProperty("prop_state")
        private String prop_state;
        @JsonProperty("prop_type")
        private String prop_type;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("imageURLs")
        public String getImageURLs() {
            return imageURLs;
        }

        @JsonProperty("imageURLs")
        public void setImageURLs(String imageURLs) {
            this.imageURLs = imageURLs;
        }

        @JsonProperty("prop_AvailDate")
        public String getProp_AvailDate() {
            return prop_AvailDate;
        }

        @JsonProperty("prop_AvailDate")
        public void setProp_AvailDate(String prop_AvailDate) {
            this.prop_AvailDate = prop_AvailDate;
        }

        @JsonProperty("prop_address")
        public String getProp_address() {
            return prop_address;
        }

        @JsonProperty("prop_address")
        public void setProp_address(String prop_address) {
            this.prop_address = prop_address;
        }

        @JsonProperty("prop_amenities")
        public String getProp_amenities() {
            return prop_amenities;
        }

        @JsonProperty("prop_amenities")
        public void setProp_amenities(String prop_amenities) {
            this.prop_amenities = prop_amenities;
        }

        @JsonProperty("prop_area")
        public String getProp_area() {
            return prop_area;
        }

        @JsonProperty("prop_area")
        public void setProp_area(String prop_area) {
            this.prop_area = prop_area;
        }

        @JsonProperty("prop_bed")
        public String getProp_bed() {
            return prop_bed;
        }

        @JsonProperty("prop_bed")
        public void setProp_bed(String prop_bed) {
            this.prop_bed = prop_bed;
        }

        @JsonProperty("prop_city")
        public String getProp_city() {
            return prop_city;
        }

        @JsonProperty("prop_city")
        public void setProp_city(String prop_city) {
            this.prop_city = prop_city;
        }

        @JsonProperty("prop_description")
        public String getProp_description() {
            return prop_description;
        }

        @JsonProperty("prop_description")
        public void setProp_description(String prop_description) {
            this.prop_description = prop_description;
        }

        @JsonProperty("prop_furnished")
        public String getProp_furnished() {
            return prop_furnished;
        }

        @JsonProperty("prop_furnished")
        public void setProp_furnished(String prop_furnished) {
            this.prop_furnished = prop_furnished;
        }

        @JsonProperty("prop_name")
        public String getProp_name() {
            return prop_name;
        }

        @JsonProperty("prop_name")
        public void setProp_name(String prop_name) {
            this.prop_name = prop_name;
        }

        @JsonProperty("prop_owner")
        public String getProp_owner() {
            return prop_owner;
        }

        @JsonProperty("prop_owner")
        public void setProp_owner(String prop_owner) {
            this.prop_owner = prop_owner;
        }

        @JsonProperty("prop_price")
        public String getProp_price() {
            return prop_price;
        }

        @JsonProperty("prop_price")
        public void setProp_price(String prop_price) {
            this.prop_price = prop_price;
        }

        @JsonProperty("prop_state")
        public String getProp_state() {
            return prop_state;
        }

        @JsonProperty("prop_state")
        public void setProp_state(String prop_state) {
            this.prop_state = prop_state;
        }

        @JsonProperty("prop_type")
        public String getProp_type() {
            return prop_type;
        }

        @JsonProperty("prop_type")
        public void setProp_type(String prop_type) {
            this.prop_type = prop_type;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }
