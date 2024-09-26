resource "yandex_compute_instance" "server1" {
  name        = var.vm_name
  platform_id = "standard-v2"

  resources {
    cores  = 2
    memory = 2
  }

  boot_disk {
    initialize_params {
      image_id = var.image_id
      size = 50
    }
  }

  network_interface {
    subnet_id = yandex_vpc_subnet.devops-subnet.id
    nat = true
    nat_ip_address = yandex_vpc_address.devops-static-ip.external_ipv4_address.0.address
  }

  metadata = {
    ssh-keys = "ubuntu:${file("~/.ssh/id_rsa.pub")}"
    user-data = "${file("./init/init.sh")}"
  }
}

output "external_ip" {
  value = yandex_compute_instance.server1.network_interface.0.nat_ip_address
}
