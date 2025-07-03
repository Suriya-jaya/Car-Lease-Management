package com.trimblecars.util;

import com.trimblecars.dto.LeaseResponseDTO;
import com.trimblecars.entity.Lease;

public class LeaseMapper {

    public static LeaseResponseDTO toDTO(Lease lease) {
        LeaseResponseDTO dto = new LeaseResponseDTO();
        dto.setLeaseId(lease.getId());
        dto.setCarId(lease.getCar().getId());
        dto.setCarModel(lease.getCar().getModel());
        dto.setCustomerId(lease.getCustomer().getId());
        dto.setCustomerName(lease.getCustomer().getName());
        dto.setStartDate(lease.getStartDate());
        dto.setEndDate(lease.getEndDate());
        return dto;
    }
}
