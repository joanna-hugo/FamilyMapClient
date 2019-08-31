package felsted.joanna.fmc.model;

import java.util.List;

public class eventListResponse {
    private List<event> data;

    public eventListResponse(List<event> data) {
        this.data = data;
    }

    public eventListResponse() { }

    public List<event> getData() {
        return data;
    }

    public void setData(List<event> data) {
        this.data = data;
    }
}
