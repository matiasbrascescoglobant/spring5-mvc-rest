package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void testGetAllVendors() {

        //given
        Vendor vendor1 = new Vendor();
        vendor1.setId(1l);
        vendor1.setName("Michale");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2l);
        vendor2.setName("Sam");

        List<Vendor> vendors = Arrays.asList(vendor1, vendor2);
        given(vendorRepository.findAll()).willReturn(vendors);

        VendorListDTO vendorDTOS = vendorService.getAllVendors();

        then(vendorRepository).should(times(1)).findAll();

        assertThat(vendorDTOS.getVendors().size(), is(equalTo(2)));

    }

    @Test
    public void testGetVendorById() {

        //given
        Vendor vendor1 = new Vendor();
        vendor1.setId(1l);
        vendor1.setName("Michale");

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor1));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        then(vendorRepository).should(times(1)).findById(anyLong());

        assertThat(vendorDTO.getName(), is(equalTo("Michale")));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() throws Exception {

        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        then(vendorRepository).should(times(1)).findById(anyLong());

    }

    @Test
    public void testCreateNewVendor() {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Michale");

        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());
        vendor.setId(1l);

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));

    }

    @Test
    public void testSaveVendorByDTO() {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Jim");

        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());
        vendor.setId(1l);

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        VendorDTO savedDTO = vendorService.saveVendorByDTO(1L, vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedDTO.getVendorUrl(), containsString("1"));

    }

    @Test
    public void testPatchVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Jim");

        Vendor vendor = new Vendor();
        vendor.setName("Jim");
        vendor.setId(1L);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        VendorDTO savedVendorDTO = vendorService.patchVendor(1L, vendorDTO);

        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    public void testDeleteVendorById() {

        Long id = 1L;

        vendorService.deleteVendorById(id);

        verify(vendorRepository, times(1)).deleteById(anyLong());

    }
}