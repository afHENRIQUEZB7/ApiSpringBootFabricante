package com.application.rest.controllers;

import com.application.rest.controllers.dto.ProductDTO;
import com.application.rest.entities.Product;
import com.application.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;


    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Product> optionalProduct = iProductService.findById(id);

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();

            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();


            return ResponseEntity.ok(productDTO);
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<?>> findAll (){
        List<ProductDTO> productList = iProductService.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()
                ).toList();


        return ResponseEntity.ok(productList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        if (productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null){
            return  ResponseEntity.badRequest().build();
        }

        productDTO.setId(null);
        iProductService.save(Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker())
                .build());

        return ResponseEntity.created(new URI("/api/product/save")).build();

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Optional<Product> product = iProductService.findById(id);

        if (product.isPresent()){
            Product product1 = product.get();

            product1.setName(productDTO.getName());
            product1.setPrice(productDTO.getPrice());
            product1.setMaker(productDTO.getMaker());

            iProductService.save(product1);

            return ResponseEntity.ok(product1);
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        Optional<Product> product = iProductService.findById(id);

        if (product.isPresent()){
            iProductService.deleteById(id);
            return ResponseEntity.ok("Registro Eliminado");
        }

        return ResponseEntity.badRequest().build();

    }


    // Metodo para mirar por el rango de precio

    @PostMapping("/range")
    public ResponseEntity<List<ProductDTO>> range(@RequestBody Map<String, BigDecimal> datos){
        BigDecimal minPrice = datos.get("minPrice");
        BigDecimal maxPrice = datos.get("maxPrice");
         List<ProductDTO> dtos = iProductService.findByPriceInRange(datos.get("minPrice"), datos.get("maxPrice")).stream()
                 .map(product -> ProductDTO.builder()
                         .id(product.getId())
                         .name(product.getName())
                         .price(product.getPrice())
                         .maker(product.getMaker())
                         .build()
                 ).toList();

         return ResponseEntity.ok(dtos);
    }



}
