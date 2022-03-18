package com.marcode.customer;

import lombok.Builder;
import lombok.Data;

//@Data
//@Builder
public record CustomerRegistrationRequest(String firstName, String lastName, String email) {}
