const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  env: {},
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'picsum.photos'
      },
      {
        protocol: 'http',
        hostname: 'localhost',
        port: '38002'
      },
    ],
  },
  logging: {
    fetches: {
      fullUrl: true,
      hmrRefreshes: true
    }
  }
}

module.exports = nextConfig
