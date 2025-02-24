import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'
import tailwindcss from "@tailwindcss/vite";


// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],
  root: 'src/main/frontend',
  build: {
    outDir: "../resources/frontend-generated",
  },
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src/main/frontend'),
    },
  }
})
