package org.optaweb.employeerostering.service.agency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AgencyService {

    @Autowired
    private final AgencyRepository agencyRepository;

    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
        Assert.notNull(agencyRepository, "agencyRepository must not be null");
    }
}
