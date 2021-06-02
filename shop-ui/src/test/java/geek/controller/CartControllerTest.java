package geek.controller;

import geek.controller.repr.ProductRepr;
import geek.persist.model.Brand;
import geek.persist.model.Category;
import geek.persist.model.Product;
import geek.persist.repo.BrandRepository;
import geek.persist.repo.CategoryRepository;
import geek.persist.repo.ProductRepository;
import geek.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

//    @Autowired
//    private CartService cartService;    //1.1 test

//    @MockBean  //спринг подминяет cartservice бином созданым Mockito    //2.2 test
//    private CartService cartService;

    private Product product;

    @BeforeEach
    public void init(){
        Brand brand = brandRepository.save(new Brand("brand"));
        Category category = categoryRepository.save(new Category("Category"));
        product = productRepository.save(new Product("Product", new BigDecimal(1234), category, brand));

    }

    @Test
    public void testAddCart() throws Exception {
        MockHttpSession session = new MockHttpSession();   // test 3.1
        mvc.perform(post("/cart")
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productId", Long.toString(product.getId()))
                .param("qty", "3")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cart"));

        session.getAttributeNames();
        CartService cartService = (CartService) session.getAttribute("scopedTarget.cartServiceImpl");

        assertFalse(cartService.getLineItems().isEmpty());
        assertEquals(1, cartService.getLineItems().size());
        assertEquals(3, cartService.getLineItems().get(0).getQty());
        assertTrue(product.getPrice().multiply(new BigDecimal(3)).compareTo(cartService.getSubTotal()) == 0);

//        mvc.perform(post("/cart")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("productId", Long.toString(product.getId()))
//                .param("qty", "3")
//        )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/cart"));

//        assertFalse(cartService.getLineItems().isEmpty());     //1.1 test проблема в проверке в том что cartService
//        в тесте не тот что создается в в приложении

//        Mockito.verify(cartService).addProductQty(any(), any(), any(), anyInt()); //2.1 test общая проверка
        // делается через mock когда не возможно использовать Autowired.

//        Mockito.verify(cartService).addProductQty(any(ProductRepr.class),
//                any(), any(), eq(3)); //2.2 test уточненая проверка

//        Mockito.verify(cartService).addProductQty(
//                argThat(repr -> repr.getId().equals(product.getId()) &&
//                repr.getTitle().equals(product.getTitle())),
//                any(), any(), eq(3)); //2.3 test уточненая проверка


    }
}
