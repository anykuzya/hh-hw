package hw.streamapi.common;

import java.util.Objects;

public class ApiPersonDto {
    private String id;
    private Long created;
    private String name;
    private Integer areaId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiPersonDto dto = (ApiPersonDto) o;
        return Objects.equals(id, dto.id) &&
            Objects.equals(created, dto.created) &&
            Objects.equals(name, dto.name) &&
            Objects.equals(areaId, dto.areaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, name, areaId);
    }
}
