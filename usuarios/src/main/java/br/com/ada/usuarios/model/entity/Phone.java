package br.com.ada.usuarios.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "countryCode")
    private String countryCode;

    @Column(name = "areaCode")
    private String areaCode;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @JoinColumn(name = "usuario_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
}
