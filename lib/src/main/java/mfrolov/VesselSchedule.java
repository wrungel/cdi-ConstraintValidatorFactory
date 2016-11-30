package mfrolov;

import javax.validation.constraints.NotNull;

/**
 * @author mfrolov
 * @since 2.5
 */
@CheckVesselScheduleIntegrity
public class VesselSchedule {

    @NotNull
    private String vesselCode;

    public VesselSchedule() {
    }

    public VesselSchedule(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }
}
