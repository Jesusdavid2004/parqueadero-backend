package com.parqueadero.controller;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.parqueadero.enums.RolUsuario;
import com.parqueadero.model.Cliente;
import com.parqueadero.model.Menu;
import com.parqueadero.model.Usuario;
import com.parqueadero.repository.ClienteRepository;
import com.parqueadero.repository.MenuRepository;
import com.parqueadero.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final MenuRepository menuRepository;

    public DebugController(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, 
                           ClienteRepository clienteRepository, MenuRepository menuRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.menuRepository = menuRepository;
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String username, @RequestParam String newPassword) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
        
        return "Contraseña actualizada para: " + username;
    }

    @PostMapping("/generate-hash")
    public String generateHash(@RequestParam String password) {
        return passwordEncoder.encode(password);
    }

    @GetMapping("/list-users")
    public String listUsers() {
        StringBuilder sb = new StringBuilder();
        usuarioRepository.findAll().forEach(u -> {
            sb.append("ID: ").append(u.getId())
              .append(" | Username: ").append(u.getUsername())
              .append(" | Email: ").append(u.getEmail())
              .append(" | Rol: ").append(u.getRol())
              .append(" | ClienteId: ").append(u.getCliente() != null ? u.getCliente().getId() : "null")
              .append("\n");
        });
        return sb.toString();
    }

    @PostMapping("/create-test-users")
    public String createTestUsers() {
        StringBuilder result = new StringBuilder();

        // Crear usuario ADMIN
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(RolUsuario.ADMIN);
            admin.setCliente(null);
            usuarioRepository.save(admin);
            result.append("Usuario ADMIN creado: admin / admin123\n");
        } else {
            result.append("Usuario admin ya existe\n");
        }

        // Crear usuario CLIENTE asociado al cliente_id=1
        if (!usuarioRepository.existsByUsername("cliente1")) {
            Cliente cliente = clienteRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Cliente con id=1 no encontrado. Ejecuta primero reset-database.sql"));
            
            Usuario clienteUser = new Usuario();
            clienteUser.setUsername("cliente1");
            clienteUser.setEmail("cliente1@test.com");
            clienteUser.setPassword(passwordEncoder.encode("1234"));
            clienteUser.setRol(RolUsuario.CLIENTE);
            clienteUser.setCliente(cliente);
            usuarioRepository.save(clienteUser);
            result.append("Usuario CLIENTE creado: cliente1 / 1234 (asociado a cliente_id=1)\n");
        } else {
            result.append("Usuario cliente1 ya existe\n");
        }

        // Crear usuario CLIENTE asociado al cliente_id=2
        if (!usuarioRepository.existsByUsername("cliente2")) {
            Cliente cliente2 = clienteRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Cliente con id=2 no encontrado. Ejecuta primero reset-database.sql"));
            
            Usuario clienteUser2 = new Usuario();
            clienteUser2.setUsername("cliente2");
            clienteUser2.setEmail("cliente2@test.com");
            clienteUser2.setPassword(passwordEncoder.encode("1234"));
            clienteUser2.setRol(RolUsuario.CLIENTE);
            clienteUser2.setCliente(cliente2);
            usuarioRepository.save(clienteUser2);
            result.append("Usuario CLIENTE creado: cliente2 / 1234 (asociado a cliente_id=2)\n");
        } else {
            result.append("Usuario cliente2 ya existe\n");
        }

        return result.toString();
    }

    @PostMapping("/test-login")
    public String testLogin(@RequestParam String username, @RequestParam String password) {
        StringBuilder result = new StringBuilder();
        
        try {
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
            
            result.append("Usuario encontrado:\n");
            result.append("  ID: ").append(usuario.getId()).append("\n");
            result.append("  Username: ").append(usuario.getUsername()).append("\n");
            result.append("  Email: ").append(usuario.getEmail()).append("\n");
            result.append("  Rol: ").append(usuario.getRol()).append("\n");
            result.append("  ClienteId: ").append(usuario.getCliente() != null ? usuario.getCliente().getId() : "null").append("\n");
            result.append("  Password en BD: ").append(usuario.getPassword()).append("\n");
            
            boolean matches = passwordEncoder.matches(password, usuario.getPassword());
            result.append("\n¿La contraseña coincide?: ").append(matches).append("\n");
            
            if (!matches) {
                String newHash = passwordEncoder.encode(password);
                result.append("Hash que se generaría para '").append(password).append("': ").append(newHash).append("\n");
            }
            
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage()).append("\n");
        }
        
        return result.toString();
    }

    @GetMapping("/ver-menus")
    public String verMenus() {
        StringBuilder result = new StringBuilder();
        result.append("=== TODOS LOS MENÚS ===\n\n");
        
        List<Menu> todos = menuRepository.findAll();
        for (Menu menu : todos) {
            result.append("ID=").append(menu.getId())
                  .append(" | Nombre=").append(menu.getNombre())
                  .append(" | Ruta=").append(menu.getRuta())
                  .append(" | Activo=").append(menu.getActivo())
                  .append(" | PadreID=").append(menu.getPadre() != null ? menu.getPadre().getId() : "null")
                  .append("\n");
        }
        
        result.append("\n=== MENÚS RAÍZ (padre_id IS NULL) ===\n\n");
        List<Menu> raiz = menuRepository.findByPadreIsNull();
        for (Menu menu : raiz) {
            result.append("ID=").append(menu.getId())
                  .append(" | Nombre=").append(menu.getNombre())
                  .append(" | Ruta=").append(menu.getRuta())
                  .append("\n");
        }
        
        return result.toString();
    }

    @PostMapping("/fix-menu-rutas")
    public String fixMenuRutas() {
        StringBuilder result = new StringBuilder();
        List<Menu> todos = menuRepository.findAll();

        for (Menu menu : todos) {
            String nombre = menu.getNombre();
            String rutaAnterior = menu.getRuta();
            boolean cambiado = false;

            if ("Dashboard".equals(nombre)) {
                menu.setRuta("/admin");
                cambiado = true;
            } else if ("Pagos".equals(nombre)) {
                menu.setRuta("/admin/pagos-facturas");
                cambiado = true;
            } else if ("Facturas".equals(nombre)) {
                menu.setRuta("/admin/pagos-facturas");
                cambiado = true;
            } else if ("Menús".equals(nombre)) {
                menu.setRuta("/admin/menu");
                cambiado = true;
            } else if ("/admin/sedes/listar".equals(menu.getRuta())) {
                menu.setRuta("/admin/sedes");
                cambiado = true;
            } else if ("/admin/sedes/crear".equals(menu.getRuta())) {
                menu.setRuta("/admin/sedes");
                cambiado = true;
            } else if ("/admin/zonas/listar".equals(menu.getRuta())) {
                menu.setRuta("/admin/zonas");
                cambiado = true;
            } else if ("/admin/zonas/crear".equals(menu.getRuta())) {
                menu.setRuta("/admin/zonas");
                cambiado = true;
            }

            if (cambiado) {
                menuRepository.save(menu);
                result.append("ID=").append(menu.getId())
                      .append(" | ").append(nombre)
                      .append(" | ").append(rutaAnterior)
                      .append(" -> ").append(menu.getRuta()).append("\n");
            }
        }

        if (result.length() == 0) {
            result.append("No se encontraron rutas para corregir.\n");
        }

        result.append("\n--- Menús actuales ---\n");
        for (Menu menu : todos) {
            result.append("ID=").append(menu.getId())
                  .append(" | ").append(menu.getNombre())
                  .append(" | ruta=").append(menu.getRuta())
                  .append(" | padre=").append(menu.getPadre() != null ? menu.getPadre().getId() : "null")
                  .append("\n");
        }

        return result.toString();
    }

    @PostMapping("/fix-menu-raiz")
    public String fixMenuRaiz() {
        StringBuilder result = new StringBuilder();

        // Verificar si ya existe el menú raíz "Admin"
        List<Menu> existentes = menuRepository.findAll().stream()
                .filter(m -> "/admin".equals(m.getRuta()) && m.getPadre() == null)
                .toList();

        Menu menuAdmin;
        if (existentes.isEmpty()) {
            menuAdmin = new Menu("Admin", "/admin", true);
            menuAdmin = menuRepository.save(menuAdmin);
            result.append("Menú raíz 'Admin' creado con id=").append(menuAdmin.getId()).append("\n");
        } else {
            menuAdmin = existentes.get(0);
            result.append("Menú raíz 'Admin' ya existe con id=").append(menuAdmin.getId()).append("\n");
        }

        // Hacer que todos los menús raíz actuales sean hijos de "Admin"
        List<Menu> menusRaiz = menuRepository.findByPadreIsNull();
        int actualizados = 0;
        for (Menu menu : menusRaiz) {
            if (!menu.getId().equals(menuAdmin.getId())) {
                menu.setPadre(menuAdmin);
                menuRepository.save(menu);
                actualizados++;
            }
        }
        result.append("Menús movidos como hijos de Admin: ").append(actualizados).append("\n");

        return result.toString();
    }
}
