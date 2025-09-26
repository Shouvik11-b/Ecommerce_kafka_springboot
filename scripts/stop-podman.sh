#!/bin/bash

echo "🛑 Stopping ecommerce API services..."

# Stop services
podman-compose -f podman-compose.yml down

echo "✅ Services stopped successfully!"

# Optionally stop Podman machine
read -p "Stop Podman machine? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    podman machine stop
    echo "🔄 Podman machine stopped"
fi
