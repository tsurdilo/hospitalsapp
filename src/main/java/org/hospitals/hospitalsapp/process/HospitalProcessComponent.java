package org.hospitals.hospitalsapp.process;

import java.util.Collections;

import org.hospitals.hospitalsapp.data.Hospital;
import org.kie.kogito.Application;
import org.kie.kogito.Model;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalProcessComponent {

    @Autowired
    Application kogitoApp;

    public ProcessInstance<?> getHospitalsAddProcess(Hospital hospital) {
        Process<? extends Model> p = kogitoApp.processes().processById("hospitals.add");

        Model m = p.createModel();
        m.fromMap(Collections.singletonMap("hospital",
                                           hospital));

        ProcessInstance<?> processInstance = p.createInstance(m);

        return processInstance;
    }

    public void startHospitalsAddProcess(Hospital hospital) {
        getHospitalsAddProcess(hospital).start();
    }
}
