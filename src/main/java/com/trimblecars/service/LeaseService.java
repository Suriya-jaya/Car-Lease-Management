package com.trimblecars.service;

import com.trimblecars.entity.Lease;

import java.util.List;

public interface LeaseService {
    Lease startLease(Long carId, Long customerId);
    Lease endLease(Long leaseId);
    List<Lease> getLeaseHistoryByCustomer(Long customerId);
    List<Lease> getLeaseHistoryByCar(Long carId);
}
