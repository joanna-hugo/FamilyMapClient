package felsted.joanna.fmc.model;

import java.util.List;

public class personListResponse {
    private List<person> data;

    public personListResponse(List<person> data) {
        this.data = data;
    }

    public personListResponse() { }

    public List<person> getData() {
        return data;
    }

    public void setData(List<person> data) {
        this.data = data;
    }
}
