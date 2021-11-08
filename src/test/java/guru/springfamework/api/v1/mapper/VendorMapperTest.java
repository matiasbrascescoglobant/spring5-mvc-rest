package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class VendorMapperTest {

    public static final String NAME = "someName";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() throws Exception {

        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(vendor.getName(), vendorDTO.getName());

    }

    @Test
    public void vendorDTOtoVendor() throws Exception {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), vendor.getName());

    }

}
